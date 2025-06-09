package com.tlog.viewmodel.beginning

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.api.TbtiDescriptionResponse
import com.tlog.data.repository.TbtiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class TbtiResultViewModel @Inject constructor(
    private val tbtiRepository: TbtiRepository
): ViewModel() {
    private val _tbtiDescription = mutableStateOf<TbtiDescriptionResponse?>(null)
    val tbtiDescription: State<TbtiDescriptionResponse?> = _tbtiDescription

    fun fetchTbtiDescription(resultCode: String) {
        viewModelScope.launch {
            try {
                val response = tbtiRepository.getTbtiDescription(resultCode)
                _tbtiDescription.value = response.data
                Log.d("TBTI", "tbtiDescription !!!! : ${_tbtiDescription.value}")
            } catch (e: Exception) {
                Log.e("TbtiTestViewModel", "설명 데이터 요청 실패", e)
            }
        }
    }
}