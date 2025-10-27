package com.tlog.data.model.team

data class Team(
    val teamId: String,
    val teamName: String,
    val teamLeaderId: String,
    val teamLeaderName: String,
    val memberIdList: List<String>
)