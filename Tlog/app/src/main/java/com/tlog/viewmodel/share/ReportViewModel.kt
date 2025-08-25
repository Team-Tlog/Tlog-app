package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel
class ReportViewModel @Inject constructor(

): ViewModel() {
    private var _title = mutableStateOf("")
    val title: State<String> = _title

    private var _content = mutableStateOf("")
    val content: State<String> = _content

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateContent(newContent: String) {
        _content.value = newContent
    }

    fun checkInput(): Boolean {
        return _title.value.isNotEmpty() && _content.value.isNotEmpty()
    }
}