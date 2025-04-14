package com.tlog.viewmodel.beginning

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


class TbtiTestViewModel: ViewModel() {
    private val _tbtiSelectList = mutableStateListOf<Int>() // 선택한 선택지의 번호를 저장하고 있는 List -> 위에꺼 1 / 아래 꺼 2
    val tbtiSelectList: List<Int> = _tbtiSelectList


    fun addList(selectNum: Int) {
        _tbtiSelectList.add(selectNum)
    }

}