package com.tlog.viewmodel.team

import androidx.compose.runtime.mutableStateListOf
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.model.team.DetailTeam
import com.tlog.data.repository.TeamRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val repository: TeamRepository
): BaseViewModel() {


    private val _teamData = MutableStateFlow<DetailTeam?>(null)
    val teamData: StateFlow<DetailTeam?> = _teamData

    private val _checkTravelList = mutableStateListOf<String>()

    fun updateCheckList(travelName: String) {
        if (_checkTravelList.contains(travelName))
            _checkTravelList.remove(travelName)
        else
            _checkTravelList.add(travelName)
    }

    fun isChecked(travelName: String): Boolean {
        return _checkTravelList.contains(travelName)
    }




    fun getTeamDetail(teamId: String) {
        launchSafeCall(
            action = {
                val result = repository.getTeamDetails(teamId)
                _teamData.value = result.data
            }
        )
    }
}
