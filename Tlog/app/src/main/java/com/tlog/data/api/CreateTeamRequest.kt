package com.tlog.data.api

data class CreateTeamRequest(
    val name: String,
    val creator: String
)

data class JoinTeamRequest(
    val inviteCode: String,
    val userId: String
)