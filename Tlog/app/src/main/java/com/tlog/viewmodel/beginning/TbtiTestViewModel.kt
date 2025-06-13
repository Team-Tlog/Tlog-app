package com.tlog.viewmodel.beginning

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.TbtiApi
import com.tlog.data.repository.TbtiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject
import com.tlog.data.api.TbtiQuestionItem
import androidx.compose.runtime.State

@HiltViewModel
class TbtiTestViewModel @Inject constructor(
    private val tbtiRepository: TbtiRepository
) : ViewModel() {

    private val _questions = mutableStateListOf<TbtiQuestionItem>()

    private val _currentQuestionIndex = mutableStateOf(0)
    val currentQuestionIndex get() = _currentQuestionIndex

    val totalQuestions: Int
        get() = _questions.size

    val currentQuestion = mutableStateOf<String?>(null)
    val currentAnswers = mutableStateOf<List<String>>(emptyList())

    private val _traitScores = mutableStateOf<Map<String, Int>>(emptyMap())

    private var _tbtiResult = mutableStateOf("")
    val tbtiResult: State<String> = _tbtiResult

    private val _sValue = mutableStateOf(0)
    val sValue: State<Int> get() = _sValue

    private val _eValue = mutableStateOf(0)
    val eValue: State<Int> get() = _eValue

    private val _lValue = mutableStateOf(0)
    val lValue: State<Int> get() = _lValue

    private val _aValue = mutableStateOf(0)
    val aValue: State<Int> get() = _aValue

    private val _resultCode = mutableStateOf<String?>(null)

    private val _resultIntCode = mutableStateOf(0)
    val tbtiIntCode: State<Int> get() = _resultIntCode

    // 중복 방지 플래그 추가
    private var alreadyFetchedQuestions = false


    fun fetchAllQuestions() {
        if (alreadyFetchedQuestions) return  // 중복 방지
        alreadyFetchedQuestions = true

        viewModelScope.launch {
            try {
                val allQuestions = mutableListOf<TbtiQuestionItem>()

                for (category in listOf("RISK_TAKING", "LOCATION_PREFERENCE", "PLANNING_STYLE", "ACTIVITY_LEVEL")) {
                    val response = tbtiRepository.getTbtiQuestions(category)
                    response.data?.let { allQuestions.addAll(it) }
                }
                _questions.clear()
                _questions.addAll(allQuestions)

                updateCurrentQuestion()
            } catch (e: Exception) {
                Log.e("TbtiTestViewModel", "질문 전체 로딩 실패", e)
            }
        }
    }


    private fun updateCurrentQuestion() {
        val index = _currentQuestionIndex.value
        if (index in _questions.indices) {
            val questionItem = _questions[index]
            currentQuestion.value = questionItem.content
            currentAnswers.value = questionItem.answers.map { it.content }
        } else {
            currentQuestion.value = null
            currentAnswers.value = emptyList()
        }
    }

    // 선택한 답변 인덱스를 저장
    val selectedAnswers = mutableListOf<Int?>()

    fun onAnswerSelected(index: Int) {
        selectedIdx.value = index
        // 현재 질문 인덱스에 사용자의 선택을 저장
        if (selectedAnswers.size <= _currentQuestionIndex.value) {
            selectedAnswers.add(index)
        } else {
            selectedAnswers[_currentQuestionIndex.value] = index
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
            Log.d("cScore", category + "   " + score.toString())
        }

        val resultCode = getSRResultCode(traitScores, categoryInitial)
        Log.d("result", categoryInitial.toString() + traitScores)
        val resultIntCode = getIntCode()

        _traitScores.value = traitScores
        _resultCode.value = resultCode
        _resultIntCode.value = resultIntCode.toInt()

        _sValue.value = traitScores["RISK_TAKING"] ?: 0
        _eValue.value = traitScores["LOCATION_PREFERENCE"] ?: 0
        _lValue.value = traitScores["PLANNING_STYLE"] ?: 0
        _aValue.value = traitScores["ACTIVITY_LEVEL"] ?: 0

        val resultList = listOf(_sValue.value.toString(), _eValue.value.toString(), _lValue.value.toString(), _aValue.value.toString())
        var retResultCode = ""

        resultList.forEachIndexed { idx, result ->
            val status = result.toInt() > 50
                when (idx) {
                    0 -> retResultCode += if (status) "S" else "R"
                    1 -> retResultCode += if (status) "O" else "E"
                    2 -> retResultCode += if (status) "L" else "N"
                    3 -> retResultCode += if (status) "I" else "A"
                }
        }


        Log.d("helloResultCode", retResultCode)

        return retResultCode
    }

    fun getIntCode(): Int {
        return "${_sValue.value}${_eValue.value}${_lValue.value}${_aValue.value}".toInt()
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

    private val _isTestFinished = mutableStateOf(false)
    val isTestFinished: State<Boolean> get() = _isTestFinished

    fun moveToNextQuestion() {
        if (_currentQuestionIndex.value < _questions.size - 1) {
            _currentQuestionIndex.value++
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
            _isTestFinished.value = true
        }
    }

    val selectedIdx = mutableStateOf<Int?>(null)
}


@Module
@InstallIn(SingletonComponent::class)
object TbtiRepositoryModule {
    @Provides
    fun provideTbtiRepository(
        retrofitInstance: TbtiApi
    ): TbtiRepository {
        return TbtiRepository(retrofitInstance)
    }

    @Provides
    fun provideTbtiApi(
        retrofit: Retrofit
    ): TbtiApi {
        return retrofit.create(TbtiApi::class.java)
    }
}