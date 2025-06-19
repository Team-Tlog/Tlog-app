package com.tlog.viewmodel.team

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.model.team.DetailTeam
import com.tlog.data.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val repository: TeamRepository
)  : ViewModel() {

    private val _teamData = mutableStateOf<DetailTeam>(
        DetailTeam(
            teamId = "",
            teamName = "",
            inviteCode = "",
            startDate = "",
            endDate = "",
            members = emptyList(),
            wishlist = emptyList()
        )
    )
    val teamData: State<DetailTeam> = _teamData


    // api
    fun getTeamDetail(teamId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getTeamDetails(teamId)
                _teamData.value = result.data
            }
            catch (e: Exception) {
                Log.d("TeamDetailViewModel", e.message.toString())
            }
        }
    }




}
