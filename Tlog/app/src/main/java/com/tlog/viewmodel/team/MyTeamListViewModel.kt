package com.tlog.viewmodel.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.model.team.Team
import com.tlog.data.repository.TeamRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MyTeamListViewModel @Inject constructor(
    private val teamRepository: TeamRepository,
    tokenProvider: TokenProvider
) : ViewModel() {
    sealed interface UiEvent {
        data class Navigate(val target: Screen, val clearBackStack: Boolean = false) : UiEvent
        data class ShowToast(val message: String) : UiEvent
    }

    private val _eventFlow = Channel<UiEvent>(Channel.BUFFERED)
    val eventFlow = _eventFlow.receiveAsFlow()


    private var userId: String? = null

    init {
        userId = tokenProvider.getUserId()
    }



    private val _teamList = mutableStateOf<List<Team>>(emptyList())
    val teamsList: State<List<Team>> = _teamList


    fun fetchTeamsFromServer() {
        viewModelScope.launch {
            try {
                val safeUserId = userId ?: return@launch
                val result = teamRepository.getTeamList(safeUserId)

                _teamList.value = result.data

            } catch (e: HttpException) {
                _eventFlow.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _eventFlow.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }

    fun deleteTeam(teamId: String) {
        viewModelScope.launch {
            try {
                teamRepository.deleteTeam(teamId)

                _teamList.value = _teamList.value.filterNot { it.teamId == teamId }
            } catch (e: HttpException) {
                _eventFlow.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _eventFlow.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }

    fun navToCreateTeam() {
        _eventFlow.trySend(UiEvent.Navigate(Screen.CreateTeam))
    }

    fun navToTeamDetail(teamId: String) {
        _eventFlow.trySend(UiEvent.Navigate(Screen.TeamDetail(teamId)))
    }

    fun navToJoinTeam() {
        _eventFlow.trySend(UiEvent.Navigate(Screen.JoinTeam))
    }
}


