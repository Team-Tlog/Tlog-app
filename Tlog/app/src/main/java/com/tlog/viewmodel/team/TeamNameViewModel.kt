package com.tlog.viewmodel.team

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TeamNameViewModel @Inject constructor() : ViewModel() {
    private var _teamName = mutableStateOf("")
    val teamName = _teamName

    fun updateTeamName(newTeamName: String) {
        _teamName.value = newTeamName
    }

    // 일단 길이제한
    fun checkTeamName(): Boolean {
        return _teamName.value.isNotEmpty() && _teamName.value.length in 2 .. 20
    }

}