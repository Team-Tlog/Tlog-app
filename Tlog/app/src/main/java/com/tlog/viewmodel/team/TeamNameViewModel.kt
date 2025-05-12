package com.tlog.viewmodel.team

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tlog.api.RetrofitInstance
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.CreateTeamRequest
import com.tlog.api.TeamApi
import kotlinx.coroutines.launch
import retrofit2.Response

class TeamNameViewModel : ViewModel() {
    var _TeamName = mutableStateOf("")
    val TeamName = _TeamName

    fun updateTeamName(NewTeamName: String) {
        _TeamName.value = NewTeamName
    }

    fun createTeam(userId: String) {
        viewModelScope.launch {
            try {
                val request = CreateTeamRequest(name = TeamName.value, creator = userId)
                val response: Response<BaseResponse<String>> =
                    RetrofitInstance.getInstance().create(TeamApi::class.java)
                        .createTeam(request)

                if (response.isSuccessful) {
                    val teamId = response.body()?.data
                    Log.d("TeamCreate", " 팀 생성 성공! 팀 ID: $teamId")
                    // 여기에 화면 전환 등의 추가 로직 넣을 수 있음
                } else {
                    Log.e("TeamCreate", "서버 오류: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("TeamCreate", "네트워크 오류: ${e.localizedMessage}")
            }
        }
    }
}
