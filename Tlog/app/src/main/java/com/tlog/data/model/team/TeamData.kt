package com.tlog.data.model.team

data class TeamData(
    val teamName: String,
    val teamTBTI: String,
    val teamStartDate: String,
    val teamEndDate: String,
    val members: List<TeamMember> = emptyList(),
    val teamDestination: String? = null,
    val teamLeader: String? = null
)
