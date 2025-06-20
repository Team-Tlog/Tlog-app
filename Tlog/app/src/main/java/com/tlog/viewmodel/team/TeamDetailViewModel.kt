package com.tlog.viewmodel.team

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.model.team.DetailTeam
import com.tlog.data.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val repository: TeamRepository
): ViewModel() {

    private val _teamData = MutableStateFlow<DetailTeam?>(null)
    val teamData: StateFlow<DetailTeam?> = _teamData


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
