package com.tlog.viewmodel.team

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import com.tlog.R
import com.tlog.data.model.TeamData

class MyTeamListViewModel : ViewModel() {
    // 서버에서 가져올 데이터라고 가정
    private val _teams = mutableStateOf(
        listOf(
            TeamData("동해번쩍팀", "제주도", "찬", listOf(R.drawable.test_image, R.drawable.test_image)),
            TeamData("서해번쩍팀", "부산", "슬기", listOf(R.drawable.test_image, R.drawable.test_image, R.drawable.test_image)),
            TeamData("남해넘실팀", "서울", "지우", listOf(R.drawable.test_image))
        )
    )

    val teams = _teams
    // 추후 서버 통신을 통해 데이터를 갱신하는 함수 예시
    fun fetchTeamsFromServer() {
        // Retrofit 등의 통신 라이브러리 사용해서 데이터 받아온 후 _teams.value = ~~ 로 갱신
    }
}
