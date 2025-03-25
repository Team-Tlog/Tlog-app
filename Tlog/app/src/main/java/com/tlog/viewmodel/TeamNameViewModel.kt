package com.tlog.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TeamNameViewModel : ViewModel(){
    var _TeamName = mutableStateOf("")
    val TeamName = _TeamName

    fun updateTeamName(NewTeamName: String) {
        _TeamName.value = NewTeamName
    }
}