package com.tlog.viewmodel.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.tlog.api.TeamApi
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.model.team.Team
import com.tlog.data.repository.TeamRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class MyTeamListViewModel @Inject constructor(
    private val teamRepository: TeamRepository,
    tokenProvider: TokenProvider
) : ViewModel() {
    private var userId: String? = null

    init {
        userId = tokenProvider.getUserId()
    }

    sealed class UiEvent {
        object ApiSuccess : UiEvent()
        data class ApiError(val message: String) : UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _teamList = mutableStateOf<List<Team>>(emptyList())
    val teamsList: State<List<Team>> = _teamList


    fun fetchTeamsFromServer() {
        viewModelScope.launch {
            try {
                val safeUserId = userId ?: return@launch
                val result = teamRepository.getTeamList(safeUserId)
                _teamList.value = result.data
                when (result.status) {
                    200 -> _eventFlow.emit(UiEvent.ApiSuccess)
                    500 -> _eventFlow.emit(UiEvent.ApiError("서버 오류가 발생했습니다."))
                    else -> _eventFlow.emit(UiEvent.ApiError("알 수 없는 오류가 발생했습니다."))
                }
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ApiError("네트워크 오류가 발생했습니다."))
            }
        }
    }

    fun deleteTeam(teamId: String) {
        viewModelScope.launch {
            try {
                val result = teamRepository.deleteTeam(teamId)
                if (result.status == 200) {
                    _teamList.value = _teamList.value.filterNot { it.teamId == teamId }
                    _eventFlow.emit(UiEvent.ApiSuccess)
                } else {
                    _eventFlow.emit(UiEvent.ApiError("삭제 실패: ${result.message}"))
                }
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ApiError("네트워크 오류가 발생했습니다."))
            }
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object MyTeamListModule {
    @Provides
    fun provideMyTeamListRepository(
        teamApi: TeamApi
    ): TeamRepository {
        return TeamRepository(teamApi)
    }

    @Provides
    fun provideTeamApi(
        retrofit: Retrofit
    ): TeamApi {
        return retrofit.create(TeamApi::class.java)
    }
}
