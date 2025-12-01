package com.tlog.viewmodel.team

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.local.TokenProvider
import com.tlog.data.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TeamJoinViewModel @Inject constructor(
    private val repository: TeamRepository,
    tokenProvider: TokenProvider
) : BaseViewModel() {
    private var userId: String? = null

    private val _codeError = MutableStateFlow(false)
    val codeError = _codeError.asStateFlow()
    private val _isCodeValid = MutableStateFlow(false)
    val isCodeValid = _isCodeValid.asStateFlow()

    val textList = mutableStateListOf<MutableState<TextFieldValue>>().apply {
        repeat(6) {
            add(mutableStateOf(TextFieldValue("")))
        }
    }

    val requesterList = List(6) { FocusRequester() }

    init {
        userId = tokenProvider.getUserId()
    }

    fun joinTeam() {
        val safeUserId = userId ?: return
        val code = textList.joinToString(separator = "") { it.value.text }

        launchSafeCall(
            action = {
                repository.joinTeam(userId = safeUserId, teamCode = code)

                popBackStack(1)
                showToast("팀 참여 성공")
            }
        )
    }

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
            _isCodeValid.value = false
            _codeError.value = false
        }
        else if (validateCode(code)) {
            _isCodeValid.value = true
            _codeError.value = false
        } else {
            _isCodeValid.value = false
            _codeError.value = true
        }
    }
}