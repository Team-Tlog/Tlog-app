package com.tlog.viewmodel.beginning

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel


class TbtiTestViewModel: ViewModel() {
    private val _tbtiSelectList = mutableStateListOf<Int>() // 선택한 선택지의 번호를 저장하고 있는 List -> 위에꺼 1 / 아래 꺼 2
    val tbtiSelectList: List<Int> = _tbtiSelectList

    private val _questionList = mutableStateListOf<String>()
    val questionList: List<String> get() = _questionList

    private val _answerLists = mutableStateListOf<List<String>>()
    val answerLists: List<List<String>> get() = _answerLists

    private val _currentQuestionIndex = mutableStateOf(0)
    val currentQuestionIndex: Int get() = _currentQuestionIndex.value

    private val _question = mutableStateOf("")
    val question: String get() = _questionList.getOrNull(_currentQuestionIndex.value) ?: ""

    private val _answerList = mutableStateOf<List<String>>(listOf())
    val answerList: List<String> get() = _answerLists.getOrNull(_currentQuestionIndex.value) ?: listOf()

    val selectedIdx: MutableState<Int?> = mutableStateOf(null)
    val selectedIndex: Int? get() = selectedIdx.value

    fun setQuestionsAndAnswers(questions: List<String>, answers: List<List<String>>) {
        _questionList.clear()
        _questionList.addAll(questions)
        _answerLists.clear()
        _answerLists.addAll(answers)
        _currentQuestionIndex.value = 0
        selectedIdx.value = null
    }

    fun addList(selectNum: Int) {
        _tbtiSelectList.add(selectNum)
    }

}