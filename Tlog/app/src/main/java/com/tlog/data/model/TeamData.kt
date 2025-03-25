package com.tlog.data.model

data class TeamData(
    val teamName: String,
    val teamTBTI: String? = null,
    val teamStartDate: String? = null,
    val teamEndDate: String? = null,
    val members: List<TeamMember> = emptyList(),
    val teamDestination: String? = null,
    val teamLeader: String? = null,
    val memberImage: List<Int> = emptyList()
)

data class TeamMember(
    val name: String,
    val tbti: String,
    val imageResId: Int
)