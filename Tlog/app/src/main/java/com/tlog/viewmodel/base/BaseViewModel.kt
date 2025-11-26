package com.tlog.viewmodel.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.common.error.toErrorMessage
import com.tlog.ui.navigation.Screen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

abstract class BaseViewModel : ViewModel() {

    sealed interface UiEvent {
        data class Navigate(val target: Screen, val clearBackStack: Boolean = false) : UiEvent
        data class ShowToast(val message: String) : UiEvent
        data class PopBackStack(val count: Int = 1) : UiEvent
    }

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    protected fun sendUiEvent(event: UiEvent) {
        _uiEvent.trySend(event)
    }

    protected fun showToast(message: String) {
        sendUiEvent(UiEvent.ShowToast(message))
    }

    protected fun navigate(target: Screen, clearBackStack: Boolean = false) {
        sendUiEvent(UiEvent.Navigate(target, clearBackStack))
    }

    protected fun popBackStack(count: Int = 1) {
        sendUiEvent(UiEvent.PopBackStack(count))
    }

    protected suspend inline fun <T> safeCall(
        action: suspend () -> T,
        onSuccess: (T) -> Unit = {},
        onError: (String) -> Unit = { showToast(it) }
    ) {
        try {
            val result = action() // 추후 바꿉시다~ 이거 사용하는 쪽으로
            onSuccess(result)
        } catch (e: HttpException) {
            Log.d("ERROR!!!", e.message.toString())
            onError(e.toErrorMessage())
        } catch (e: Exception) {
            Log.d("ERROR!!!", e.message.toString())
            onError(e.toErrorMessage())
        }
    }

    protected fun <T>  launchSafeCall(
        action: suspend () -> (T),
        onSuccess: (T) -> Unit = {},
        onError: (String) -> Unit = { showToast(it) }
    ) {
        viewModelScope.launch {
            safeCall(
                action = action,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }
}