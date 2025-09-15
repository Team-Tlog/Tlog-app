package com.tlog.viewmodel.beginning

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.repository.TbtiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import com.tlog.data.model.tbti.TbtiQuestion
import com.tlog.ui.navigation.Screen

@HiltViewModel
class TbtiTestViewModel @Inject constructor(
    private val tbtiRepository: TbtiRepository
) : BaseViewModel() {

    private val _questions = mutableStateListOf<TbtiQuestion>()

    private val _currentQuestionIndex = mutableIntStateOf(0)
    val currentQuestionIndex get() = _currentQuestionIndex

    val totalQuestions: Int
        get() = _questions.size

    val currentQuestion = mutableStateOf<String?>(null)
    val currentAnswers = mutableStateOf<List<String>>(emptyList())

    private val _traitScores = mutableStateOf<Map<String, Int>>(emptyMap())

    private var _tbtiResult = mutableStateOf("")
    val tbtiResult: State<String> = _tbtiResult

    private val _sValue = mutableIntStateOf(0)
    val sValue: State<Int> = _sValue

    private val _eValue = mutableIntStateOf(0)
    val eValue: State<Int> get() = _eValue

    private val _lValue = mutableIntStateOf(0)
    val lValue: State<Int> get() = _lValue

    private val _aValue = mutableIntStateOf(0)
    val aValue: State<Int> get() = _aValue

    private val _resultCode = mutableStateOf<String?>(null)

    private val _resultIntCode = mutableIntStateOf(0)
    val tbtiIntCode: State<Int> get() = _resultIntCode

    // 중복 방지 플래그 추가
    private var alreadyFetchedQuestions = false

    // 선택한 답변 인덱스를 저장
    val selectedAnswers = mutableListOf<Int?>()


    val selectedIdx = mutableStateOf<Int?>(null)


    fun fetchAllQuestions() {
        if (alreadyFetchedQuestions) return  // 중복 방지

        alreadyFetchedQuestions = true

        launchSafeCall(
            action = {
                val allQuestions = mutableListOf<TbtiQuestion>()

                for (category in listOf("RISK_TAKING", "LOCATION_PREFERENCE", "PLANNING_STYLE", "ACTIVITY_LEVEL")) {
                    val response = tbtiRepository.getTbtiQuestions(category)
                    response.data.let { allQuestions.addAll(it) }
                }
                _questions.clear()
                _questions.addAll(allQuestions)

                updateCurrentQuestion()
            },
            onError = { Log.e("TbtiTestViewModel", it) }
        )
    }


    private fun updateCurrentQuestion() {
        val index = _currentQuestionIndex.intValue
        if (index in _questions.indices) {
            val questionItem = _questions[index]
            currentQuestion.value = questionItem.content
            currentAnswers.value = questionItem.answers.map { it.content }
        } else {
            currentQuestion.value = null
            currentAnswers.value = emptyList()
        }
    }


    fun onAnswerSelected(index: Int) {
        selectedIdx.value = index
        // 현재 질문 인덱스에 사용자의 선택을 저장
        if (selectedAnswers.size <= _currentQuestionIndex.intValue) {
            selectedAnswers.add(index)
        } else {
            selectedAnswers[_currentQuestionIndex.intValue] = index
        }
    }

    fun calculateResultCode(
        userSelections: Map<String, List<Int>>, // 카테고리별 선택 (왼쪽 0, 오른쪽 1, ... 등)
        categoryInitial: Map<String, String> // 서버에서 받은 categoryInitial (예: "EI": "E-I")
    ): String {
        val traitScores = mutableMapOf<String, Int>()
        userSelections.forEach { (category, selections) ->
            val questions = _questions.filter { it.traitCategory == category }
            var weightedSum = 0.0
            var totalWeight = 0

            questions.forEachIndexed { index, question ->
                val selectedIndex = selections.getOrNull(index) ?: 0
                val selectedPercentage = question.answers.getOrNull(selectedIndex - 1)?.percentage ?: 0

                weightedSum += selectedPercentage.toDouble() * question.weight.toDouble()
                totalWeight += question.weight
            }

            val score = if (totalWeight == 0) 0 else (weightedSum / totalWeight).toInt()
            traitScores[category] = score
//            Log.d("cScore", category + "   " + score.toString())
        }

        val resultCode = getSRResultCode(traitScores, categoryInitial)
//        Log.d("result", categoryInitial.toString() + traitScores)
        val resultIntCode = getIntCode()

        _traitScores.value = traitScores
        _resultCode.value = resultCode
        _resultIntCode.intValue = resultIntCode.toInt()

        _sValue.intValue = traitScores["RISK_TAKING"] ?: 0
        _eValue.intValue = traitScores["LOCATION_PREFERENCE"] ?: 0
        _lValue.intValue = traitScores["PLANNING_STYLE"] ?: 0
        _aValue.intValue = traitScores["ACTIVITY_LEVEL"] ?: 0

        val resultList = listOf(_sValue.intValue.toString(), _eValue.intValue.toString(), _lValue.intValue.toString(), _aValue.intValue.toString())
        var retResultCode = ""

        resultList.forEachIndexed { idx, result ->
            val status = result.toInt() >= 50
                when (idx) {
                    0 -> retResultCode += if (status) "S" else "R"
                    1 -> retResultCode += if (status) "O" else "E"
                    2 -> retResultCode += if (status) "L" else "N"
                    3 -> retResultCode += if (status) "I" else "A"
                }
        }


//        Log.d("ResultCode", retResultCode)

        return retResultCode
    }

    fun getIntCode(): Int {
        return "${_sValue.intValue}${_eValue.intValue}${_lValue.intValue}${_aValue.intValue}".toInt()
    }

    fun getSRResultCode(
        traitScores: Map<String, Int>,
        categoryInitial: Map<String, String>
    ): String {
        val resultCode = StringBuilder()

        traitScores.forEach { (category, score) ->
            val initials = categoryInitial[category]?.split("-")
            if (initials != null && initials.size == 2) {
                val selected = if (score in 0..49) initials[0] else initials[1]
                resultCode.append(selected)
            } else {
                resultCode.append("?")
            }
        }

        return resultCode.toString()
    }



    fun moveToNextQuestion() {
        if (_currentQuestionIndex.intValue < _questions.size - 1) {
            _currentQuestionIndex.intValue++
            updateCurrentQuestion()
        } else {
            // 마지막 질문까지 답변했으면 결과 계산
            val userSelections = mutableMapOf<String, List<Int>>()
            _questions.groupBy { it.traitCategory }.forEach { (category, questions) ->
                val selections = questions.map {question ->
                val index = _questions.indexOf(question)
                    selectedAnswers.getOrNull(index) ?: 0
                }

                userSelections[category] = selections
            }

            val categoryInitial = _questions.associate { it.traitCategory to it.categoryIntial }

            _tbtiResult.value = calculateResultCode(userSelections, categoryInitial)
            navigate(Screen.TbtiResult(
                _tbtiResult.value,
                sValue.value.toString(),
                eValue.value.toString(),
                lValue.value.toString(),
                aValue.value.toString()
            ), true)
        }
    }
}
