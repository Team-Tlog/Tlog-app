package com.tlog.viewmodel.team

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.repository.TeamRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TeamJoinViewModel @Inject constructor(
    private val repository: TeamRepository,
    tokenProvider: TokenProvider
) : BaseViewModel() {

    private var userId: String? = null

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