package com.tlog.viewmodel.team

import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.dto.team.TeamDetailDto
import com.tlog.data.repository.TeamRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val repository: TeamRepository
): BaseViewModel() {
    private val _teamData = MutableStateFlow<TeamDetailDto?>(null)
    val teamData: StateFlow<TeamDetailDto?> = _teamData

    private val _checkTravels = MutableStateFlow<List<String>>(emptyList())
    val checkTravels = _checkTravels.asStateFlow()

    fun updateCheckList(travelName: String) {
        if (_checkTravels.value.contains(travelName))
            _checkTravels.value = _checkTravels.value.minus(travelName)
        else
            _checkTravels.value = _checkTravels.value.plus(travelName)
    }

    fun isChecked(travelName: String): Boolean {
        return _checkTravels.value.contains(travelName)
    }

    fun getTeamDetail(teamId: String) {
        launchSafeCall(
            action = {
                val result = repository.getTeamDetails(teamId)
                _teamData.value = result.data
            }
        )
    }

    fun navToAiCourse() {
        navigate(Screen.AiCourseInput(isTeam = true))
    }
}
