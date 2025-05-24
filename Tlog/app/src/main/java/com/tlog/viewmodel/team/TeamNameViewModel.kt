package com.tlog.viewmodel.team

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.tlog.api.TeamApi
import com.tlog.data.api.CreateTeamRequest
import com.tlog.data.local.UserPreferences
import com.tlog.data.repository.TeamNameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamNameViewModel @Inject constructor(
    private val repository: TeamNameRepository
) : ViewModel() {

    sealed class UiEvent {
        object ApiSuccess: UiEvent()
        data class ApiError(val message: String): UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var userId: String? = null

    fun initUserId(context: Context) {
        viewModelScope.launch {
            userId = UserPreferences.getUserId(context)
        }
    }

    var _TeamName = mutableStateOf("")
    val TeamName = _TeamName

    fun updateTeamName(NewTeamName: String) {
        _TeamName.value = NewTeamName
    }

    fun createTeam() {
        viewModelScope.launch {
            val safeUserId = userId ?: return@launch // null이면 launch 종료 (안돌아감)

            try {
                val result = repository.createTeam(CreateTeamRequest( //data에 팀아이디가 옴
                    name = TeamName.value,
                    creator = safeUserId
                    )
                )
                when (result.status) {
                    200 -> _eventFlow.emit(UiEvent.ApiSuccess)
                    else -> _eventFlow.emit(UiEvent.ApiError(result.message))
                }
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ApiError("네트워크 오류가 발생했습니다."))
            }
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object ReviewModule {
    @Provides
    fun provideTeamNameRepository(
        teamApi: TeamApi
    ): TeamNameRepository {
        return TeamNameRepository(teamApi)
    }
}