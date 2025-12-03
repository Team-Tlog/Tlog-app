package com.tlog.data.dto.team

data class TeamDto(
    val teamId: String,
    val teamName: String,
    val teamLeaderId: String,
    val teamLeaderName: String,
    val memberIdList: List<String>
)
