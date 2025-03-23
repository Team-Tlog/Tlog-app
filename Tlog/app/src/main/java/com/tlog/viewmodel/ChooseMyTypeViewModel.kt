package com.tlog.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ChooseDestinationViewModel : ViewModel() {
    private val _selected = mutableStateOf(setOf<String>())
    val selected get() = _selected

    fun toggleSelection(name: String, maxSelection: Int) {
        _selected.value = if (_selected.value.contains(name)) {
            _selected.value - name
        } else {
            if (_selected.value.size < maxSelection) _selected.value + name
            else _selected.value
        }
    }
}
