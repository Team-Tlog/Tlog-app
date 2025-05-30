package com.tlog.viewmodel.beginning

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class TbtiTestViewModel: ViewModel() {
    private val _tbtiSelectList = mutableStateListOf<Int>() // 선택한 선택지의 번호를 저장하고 있는 List -> 위에꺼 1 / 아래 꺼 2
    val tbtiSelectList: List<Int> = _tbtiSelectList

    private val _question = mutableStateOf("여행을 가기 전날 밤 내 가방의 상태는?")
    val question: String get() = _question.value

    private val _answerList = mutableStateOf<List<String>>(listOf("이미 갈 준비 완료! 편하게 자볼까?", "벌써 내일이야? 빨리 짐 싸자!!"))
    val answerList: List<String> get() = _answerList.value

    fun addList(selectNum: Int) {
        _tbtiSelectList.add(selectNum)
    }

}