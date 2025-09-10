package com.tlog.viewmodel.team

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.repository.TeamRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class TeamJoinViewModel @Inject constructor(
    private val repository: TeamRepository,
    tokenProvider: TokenProvider
) : ViewModel() {
    sealed interface UiEvent {
        data class Navigate(val target: Screen, val clearBackStack: Boolean = false): UiEvent
        data class PopBackStack(val count: Int = 1): UiEvent
        data class ShowToast(val message: String): UiEvent
    }

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    private var userId: String? = null

    init {
        userId = tokenProvider.getUserId()
    }

    fun joinTeam() {
        viewModelScope.launch {
            val safeUserId = userId ?: return@launch
            val code = textList.joinToString(separator = "") { it.value.text }

            try {
                repository.joinTeam(userId = safeUserId, teamCode = code)

                _uiEvent.trySend(UiEvent.PopBackStack())
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }





    val codeError = mutableStateOf(false)
    val isCodeValid = mutableStateOf(false)

    val textList = mutableStateListOf<MutableState<TextFieldValue>>().apply {
        repeat(6) {
            add(mutableStateOf(TextFieldValue("")))
        }
    }

    val requesterList = List(6) { FocusRequester() }

    fun checkCodeValid() {
        val code = textList.joinToString(separator = "") { it.value.text }

        onCodeEntered(code)

       // codeError.value = code.length == 6 && codeError.value
        //isCodeValid.value = code.length == 6 && isCodeValid.value
    }

    fun validateCode(code: String): Boolean {
        return code.length == 6
    }

    fun onCodeEntered(code: String) {
        if (code.isEmpty()) {
            isCodeValid.value = false
            codeError.value = false
        }
        else if (validateCode(code)) {
            isCodeValid.value = true
            codeError.value = false
        } else {
            isCodeValid.value = false
            codeError.value = true
        }
    }
}