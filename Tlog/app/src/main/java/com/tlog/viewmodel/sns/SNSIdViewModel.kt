package com.tlog.viewmodel.sns

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SNSIdViewModel : ViewModel() {

    // 입력된 ID
    private val _id = MutableStateFlow("")
    val id: StateFlow<String> = _id

    // 중복 여부 (true: 중복됨, false: 사용 가능, null: 아직 입력 안함)
    private val _isDuplicated = MutableStateFlow<Boolean?>(null)
    val isDuplicated: StateFlow<Boolean?> = _isDuplicated

    fun updateId(newId: String) {
        _id.value = newId
        checkDuplicate(newId)
    }

    private fun checkDuplicate(id: String) {
        // 하드코딩된 중복 리스트 (나중에 서버로 대체 예정)
        val existingIds = listOf("tlog", "computer")
        _isDuplicated.value = id in existingIds
    }
}
