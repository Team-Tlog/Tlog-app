package com.tlog.viewmodel.share

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class SearchViewModel: ViewModel(){
    var _searchText = mutableStateOf("")
    val searchText = _searchText

    fun updateSearchText(NewSearchText: String) {
        _searchText.value = NewSearchText
    }
}