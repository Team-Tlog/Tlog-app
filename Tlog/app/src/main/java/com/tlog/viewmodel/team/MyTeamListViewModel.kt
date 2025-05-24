package com.tlog.viewmodel.team

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.tlog.api.RetrofitInstance
import com.tlog.api.TeamApi
import com.tlog.data.api.TeamData
import com.tlog.data.local.UserPreferences
import com.tlog.data.repository.MyTeamListRepository
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
class MyTeamListViewModel @Inject constructor(
    private val myTeamListRepository: MyTeamListRepository
) : ViewModel() {

    sealed class UiEvent {
        object ApiSuccess : UiEvent()
        data class ApiError(val message: String) : UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _teamList = mutableStateOf<List<TeamData>>(emptyList())
    val teamsList: State<List<TeamData>> = _teamList

    private var userId: String? = null

    fun initUserId(context: Context) {
        viewModelScope.launch {
            userId = UserPreferences.getUserId(context)
        }
    }

    fun fetchTeamsFromServer() {
        viewModelScope.launch {
            try {
                val safeUserId = userId ?: return@launch
                val result = myTeamListRepository.getTeamList(safeUserId)
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
}

@Module
@InstallIn(SingletonComponent::class)
object MyTeamListModule {
    @Provides
    fun provideMyTeamListRepository(
        teamApi: TeamApi
    ): MyTeamListRepository {
        return MyTeamListRepository(teamApi)
    }

    @Provides
    fun provideTeamApi(): TeamApi {
        return RetrofitInstance.getInstance().create(TeamApi::class.java)
    }
}
