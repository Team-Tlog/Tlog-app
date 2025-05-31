package com.tlog.viewmodel.beginning


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel

class TbtiCodeInputViewModel : ViewModel() {

    val codeError = mutableStateOf(false)
    val isCodeValid = mutableStateOf(false)

    val textList = mutableStateListOf<MutableState<TextFieldValue>>().apply {
        repeat(8) {
            add(mutableStateOf(TextFieldValue("")))
        }
    }

    val requesterList = List(8) { FocusRequester() }

    fun checkCodeValid() {
        val code = textList.joinToString(separator = "") { it.value.text }
        codeError.value = code.length == 8 && codeError.value
        isCodeValid.value = code.length == 8 && isCodeValid.value
    }

    fun validateCode(code: String): Boolean {
        if (code.length != 8 || !code.all { it.isDigit() }) return false
        val split = code.chunked(2)
        return split.all {
            val num = it.toIntOrNull() ?: return@all false
            num in 1..99
        }
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
