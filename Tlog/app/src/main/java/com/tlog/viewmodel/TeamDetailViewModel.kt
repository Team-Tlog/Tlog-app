package com.tlog.viewmodel

import androidx.lifecycle.ViewModel
import com.tlog.R
import com.tlog.data.model.TeamData
import com.tlog.data.model.TeamMember

class TeamDetailViewModel : ViewModel() {
    val teamData = TeamData(
        teamName = "팀별명",
        teamTBTI = "ISTP",
        teamStartDate = "2025.01.02",
        teamEndDate = "2025.02.04",
        members = listOf(
            TeamMember("홍길동", "ISTP", R.drawable.test_image),
            TeamMember("김여행", "ENFP", R.drawable.test_image),
            TeamMember("이탐험", "INFJ", R.drawable.test_image),
            TeamMember("박바다", "ESTJ", R.drawable.test_image)
        )
    )
}
