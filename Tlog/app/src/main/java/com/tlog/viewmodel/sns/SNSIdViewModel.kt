package com.tlog.viewmodel.sns

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.local.UserPreferences
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.repository.SnsRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SNSIdViewModel @Inject constructor(
    private val repository: SnsRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    sealed interface UiEvent {
        data class Navigate(val target: Screen, val clearBackStack: Boolean = false): UiEvent
        data class ShowToast(val message: String): UiEvent
    }

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

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
                _uiEvent.trySend(UiEvent.Navigate(Screen.SnsMain))
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }


    fun updateId(newId: String) {
        _snsId.value = newId
        _isDuplicated.value = false
    }
}
