package com.tlog.viewmodel

import androidx.lifecycle.ViewModel
import com.tlog.R
import com.tlog.data.model.team.TeamData
import com.tlog.data.model.team.TeamMember

class TeamDetailViewModel : ViewModel() {
    val teamData = TeamData(
        teamName = "팀별명",
        teamTBTI = "ISTP",
        teamStartDate = "2025.01.02",
        teamEndDate = "2025.02.04",
        members = listOf(
            TeamMember("홍길동", "ISTP", ""),
            TeamMember("김여행", "ENFP", ""),
            TeamMember("이탐험", "INFJ", ""),
            TeamMember("박바다", "ESTJ", "")
        )
    )
}
