package com.tlog.viewmodel.team

import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TeamNameViewModel @Inject constructor() : BaseViewModel() {
    private val _teamName = MutableStateFlow("")
    val teamName = _teamName.asStateFlow()

    fun updateTeamName(newTeamName: String) {
        _teamName.value = newTeamName
    }

    // 일단 길이제한
    fun checkTeamName(): Boolean {
        return _teamName.value.isNotEmpty() && _teamName.value.length in 2 .. 20
    }

    fun navToTeamInfoInput(teamName: String) {
        navigate(Screen.TeamInfoInput(teamName))
    }
}