package com.tlog.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class SearchViewModel: ViewModel(){
    var searchText by mutableStateOf("")

    fun updateSearchText(NewSearchText: String) {
        searchText = NewSearchText
    }
}