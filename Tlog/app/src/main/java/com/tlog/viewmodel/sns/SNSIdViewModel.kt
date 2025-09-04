package com.tlog.viewmodel.sns

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.local.UserPreferences
import com.tlog.data.model.share.toErrorMessage
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
    private val _isDuplicated = mutableStateOf(false)
    val isDuplicated: State<Boolean?> = _isDuplicated


    fun updateSnsId(id: String) {
        viewModelScope.launch {
            try {
                repository.updateSnsId(id)

                userPreferences.setSnsId(id)
                _eventFlow.emit(UiEvent.ApiSuccess)
            } catch (e: HttpException) {
                _eventFlow.emit(UiEvent.ApiError(e.toErrorMessage()))
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ApiError(e.toErrorMessage()))
            }
        }
    }


    fun updateId(newId: String) {
        _snsId.value = newId
        _isDuplicated.value = false
    }
}
