package com.tlog.data.api

data class TeamData(
    val teamId: String,
    val teamName: String,
    val teamLeaderName: String,
    val memberIdList: List<String>
)
