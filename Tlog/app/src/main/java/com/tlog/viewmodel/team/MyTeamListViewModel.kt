package com.tlog.viewmodel.team

import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.local.TokenProvider
import com.tlog.data.model.team.Team
import com.tlog.data.repository.TeamRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyTeamListViewModel @Inject constructor(
    private val teamRepository: TeamRepository,
    tokenProvider: TokenProvider,
) : BaseViewModel() {
    private var userId: String? = null

    private val _teams = MutableStateFlow<List<Team>>(emptyList())
    val teams = _teams.asStateFlow()

    init {
        userId = tokenProvider.getUserId()
    }

    fun fetchTeamsFromServer() {
        launchSafeCall(
            action = {
                val safeUserId = userId ?: return@launchSafeCall
                val result = teamRepository.getTeamList(safeUserId)

                _teams.value = result.data
            }
        )
    }

    fun deleteTeam(teamId: String, teamLeaderId: String) {
        launchSafeCall(
            action = {
                if (teamLeaderId == userId) {
                    teamRepository.deleteTeam(teamId)

                    _teams.value = _teams.value.filterNot { it.teamId == teamId }
                    showToast("팀 삭제 성공")
                } else {
                    teamRepository.leaveTeam(teamId, userId!!)

                    _teams.value = _teams.value.filterNot { it.teamId == teamId }
                    showToast("팀 떠나기 성공")
                }
            }
        )
    }


    fun navToCreateTeam() {
        navigate(Screen.CreateTeam)
    }

    fun navToTeamDetail(teamId: String) {
        navigate(Screen.TeamDetail(teamId))
    }

    fun navToJoinTeam() {
        navigate(Screen.JoinTeam)
    }
}


