package com.tlog.viewmodel.team

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.share.SearchViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class TeamNameViewModel @Inject constructor() : ViewModel() {
    sealed interface UiEvent {
        data class Navigate(val target: Screen, val clearBackStack: Boolean = false): UiEvent
        data class ShowToast(val message: String): UiEvent
    }

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    private var _teamName = mutableStateOf("")
    val teamName = _teamName

    fun updateTeamName(newTeamName: String) {
        _teamName.value = newTeamName
    }

    // 일단 길이제한
    fun checkTeamName(): Boolean {
        return _teamName.value.isNotEmpty() && _teamName.value.length in 2 .. 20
    }

    fun navToTeamInfoInput(teamName: String) {
        _uiEvent.trySend(UiEvent.Navigate(Screen.TeamInfoInput(teamName)))
    }
}