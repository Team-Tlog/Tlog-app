package com.tlog.viewmodel.team

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.tlog.api.RetrofitInstance
import com.tlog.api.TeamApi
import com.tlog.data.api.TeamData
import kotlinx.coroutines.launch

class MyTeamListViewModel : ViewModel() {

    private val _teams = mutableStateOf<List<TeamData>>(emptyList())
    val teams: State<List<TeamData>> = _teams

    fun fetchTeamsFromServer(userId: String) {
        viewModelScope.launch {
            try {
                val teamApi = RetrofitInstance.getInstance().create(TeamApi::class.java)
                val response = teamApi.getTeamList(userId)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _teams.value = body.data
                        Log.d("MyTeamListVM", "팀 리스트 불러오기 성공: ${body.data}")
                    } else {
                        Log.e("MyTeamListVM", "응답은 성공했지만 body가 null입니다.")
                    }
                } else {
                    Log.e("MyTeamListVM", "서버 오류: code=${response.code()}, message=${response.body()}")
                }
            } catch (e: Exception) {
                Log.e("MyTeamListVM", "네트워크 오류: ${e.localizedMessage}", e)
            }
        }
    }
}
