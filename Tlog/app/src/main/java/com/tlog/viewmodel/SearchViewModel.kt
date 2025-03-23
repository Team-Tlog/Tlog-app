package com.tlog.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class SearchViewModel: ViewModel(){
    var _searchText = mutableStateOf("")
    val searchText = _searchText

    fun updateSearchText(NewSearchText: String) {
        _searchText.value = NewSearchText
    }
}