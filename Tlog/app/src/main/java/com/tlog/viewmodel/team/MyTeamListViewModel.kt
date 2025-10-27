package com.tlog.viewmodel.team

import com.tlog.viewmodel.base.BaseViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.model.team.Team
import com.tlog.data.repository.TeamRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyTeamListViewModel @Inject constructor(
    private val teamRepository: TeamRepository,
    tokenProvider: TokenProvider,
) : BaseViewModel() {


    private var userId: String? = null

    init {
        userId = tokenProvider.getUserId()
    }


    private val _teamList = mutableStateOf<List<Team>>(emptyList())
    val teamsList: State<List<Team>> = _teamList


    fun fetchTeamsFromServer() {
        launchSafeCall(
            action = {
                val safeUserId = userId ?: return@launchSafeCall
                val result = teamRepository.getTeamList(safeUserId)

                _teamList.value = result.data
            }
        )
    }

    fun deleteTeam(teamId: String, teamLeaderId: String) {
        launchSafeCall(
            action = {
                if (teamLeaderId == userId) {
                    teamRepository.deleteTeam(teamId)

                    _teamList.value = _teamList.value.filterNot { it.teamId == teamId }
                    showToast("팀 삭제 성공")
                } else {
                    teamRepository.leaveTeam(teamId, userId!!)

                    _teamList.value = _teamList.value.filterNot { it.teamId == teamId }
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


