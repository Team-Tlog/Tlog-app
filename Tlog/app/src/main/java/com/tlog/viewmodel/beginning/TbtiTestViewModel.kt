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

@HiltViewModel
class TbtiTestViewModel @Inject constructor(
    private val tbtiRepository: TbtiRepository
) : ViewModel() {

    private val _questions = mutableStateListOf<TbtiQuestionItem>()
    val questions: List<TbtiQuestionItem> get() = _questions

    private val _currentQuestionIndex = mutableStateOf(0)
    val currentQuestionIndex get() = _currentQuestionIndex

    val totalQuestions: Int
        get() = _questions.size

    val currentQuestion = mutableStateOf<String?>(null)
    val currentAnswers = mutableStateOf<List<String>>(emptyList())

    init {
        fetchAllQuestions()  // 앱 시작 시 한 번에 질문 로딩
    }

    private fun fetchAllQuestions() {
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
        userSelections: Map<String, List<Int>>, // 카테고리별 선택 (왼쪽 0, 오른쪽 1)
        categoryInitial: Map<String, String> // 서버에서 받은 categoryInitial (예: "EI": "E-I")
    ): String {
        // 1️⃣ 카테고리별 점수 계산
        val traitScores = mutableMapOf<String, Int>()
        userSelections.forEach { (category, selections) ->
            // 오른쪽 선택 비율 = (오른쪽 선택 수) / (총 선택 수)
            val total = selections.size
            val rightSelections = selections.count { it == 1 }
            val score = if (total == 0) 0 else (rightSelections.toDouble() / total * 100).toInt()
            traitScores[category] = score
        }

        // 2️⃣ 점수 기반으로 알파벳 선택
        val resultCode = getSRResultCode(traitScores, categoryInitial)

        // 3️⃣ 로그 출력 (테스트 단계)
        Log.d("ResultCode", "최종 4자리 알파벳: $resultCode")

        return resultCode
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
        val userSelections = mutableMapOf<String, List<Int>>()
        _questions.groupBy { it.traitCategory }.forEach { (category, questions) ->
            val selections = questions.mapIndexed { index, _ ->
                selectedAnswers.getOrNull(index) ?: 0  // 선택하지 않은 경우 기본값 0
            }
            userSelections[category] = selections
        }

        val categoryInitial = _questions.associate { it.traitCategory to it.categoryIntial }

        val result = calculateResultCode(userSelections, categoryInitial)
        Log.d("TbtiTestViewModel", "최종 결과: $result")
    }

    val selectedIdx = mutableStateOf<Int?>(null)
}


@Module
@InstallIn(SingletonComponent::class)
object TbtiRepositoryModule {
    @Provides
    fun provideTbtiRepository(
        tbtiApi: TbtiApi
    ): TbtiRepository {
        return TbtiRepository(tbtiApi)
    }

    @Provides
    fun provideTbtiApi(
        retrofit: Retrofit
    ): TbtiApi {
        return retrofit.create(TbtiApi::class.java)
    }
}