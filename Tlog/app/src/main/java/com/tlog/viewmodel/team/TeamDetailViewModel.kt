package com.tlog.viewmodel.team

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.model.team.DetailTeam
import com.tlog.data.repository.TeamRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val repository: TeamRepository
): ViewModel() {
    sealed interface UiEvent {
        data class Navigate(val target: Screen, val clearBackStack: Boolean = false): UiEvent

        data class ShowToast(val message: String): UiEvent
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


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
        viewModelScope.launch {
            try {
                val result = repository.getTeamDetails(teamId)

                _teamData.value = result.data
            } catch (e: HttpException) {
                _eventFlow.emit(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }
}
