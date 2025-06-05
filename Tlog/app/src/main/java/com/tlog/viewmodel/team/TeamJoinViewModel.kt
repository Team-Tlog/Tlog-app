package com.tlog.viewmodel.team

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamJoinViewModel @Inject constructor(
    private val repository: TeamRepository,
    tokenProvider: TokenProvider
) : ViewModel() {
    sealed class UiEvent {
        object ApiSuccess : UiEvent()
        data class ApiError(val message: String) : UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()




    private var userId: String? = null

    init {
        userId = tokenProvider.getUserId()
    }

    fun joinTeam() {
        viewModelScope.launch {
            val safeUserId = userId ?: return@launch
            val code = textList.joinToString(separator = "") { it.value.text }
            try {
                val result = repository.joinTeam(userId = safeUserId, teamCode = code)

                when(result.status) {
                    200 -> _eventFlow.emit(UiEvent.ApiSuccess)
                    else -> _eventFlow.emit(UiEvent.ApiError("참여 실패"))
                }
            }
            catch (e: Exception) {
                Log.d("TeamJoinViewModel", e.message.toString())
                _eventFlow.emit(UiEvent.ApiError("참여 실패"))
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
        codeError.value = code.length == 6 && codeError.value
        isCodeValid.value = code.length == 6 && isCodeValid.value
    }

    fun validateCode(code: String): Boolean {
        return code.length == 6
    }

    fun onCodeEntered(code: String) {
        if (validateCode(code)) {
            isCodeValid.value = true
            codeError.value = false
        } else {
            isCodeValid.value = false
            codeError.value = true
        }
    }
}