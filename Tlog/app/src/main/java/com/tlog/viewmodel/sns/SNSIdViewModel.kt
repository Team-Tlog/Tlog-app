package com.tlog.viewmodel.sns

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.local.UserPreferences
import com.tlog.data.repository.SnsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SNSIdViewModel @Inject constructor(
    private val repository: SnsRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    sealed class UiEvent {
        object ApiSuccess : UiEvent()
        data class ApiError(val message: String) : UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // 입력된 ID
    private val _snsId = mutableStateOf("")
    val snsId: State<String> = _snsId

    // 중복 여부 (true: 중복됨, false: 사용 가능, null: 아직 입력 안함)
    private val _isDuplicated = mutableStateOf<Boolean>(false)
    val isDuplicated: State<Boolean?> = _isDuplicated


    fun updateSnsId(id: String) {
        viewModelScope.launch {
            try {
                val result = repository.updateSnsId(id)

                when (result.status) {
                    200 -> {
                        _eventFlow.emit(UiEvent.ApiSuccess)
                        userPreferences.setSnsId(id)
                    }
                    404 -> _eventFlow.emit(UiEvent.ApiError(result.message ?: "사용자 정보 없음"))
                    409 -> {
                        _eventFlow.emit(UiEvent.ApiError(result.message ?: "중복된 ID"))
                        _isDuplicated.value = true
                    }
                    500 -> _eventFlow.emit(UiEvent.ApiError(result.message ?: "서버 오류"))
                    else -> _eventFlow.emit(UiEvent.ApiError(result.message ?: "알 수 없는 오류"))
                }
            }
            catch (e: HttpException) {
                when (e.code()) {
                    404 -> _eventFlow.emit(UiEvent.ApiError("사용자 정보 없음"))
                    409 -> {
                        _isDuplicated.value = true
                    }
                    500 -> _eventFlow.emit(UiEvent.ApiError("서버 오류"))
                    else -> _eventFlow.emit(UiEvent.ApiError("알 수 없는 오류"))
                }
                Log.d("SNSIdViewModel", e.message ?: "알 수 없는 오류")
            }
        }
    }


    fun updateId(newId: String) {
        _snsId.value = newId
        _isDuplicated.value = false
    }
}
