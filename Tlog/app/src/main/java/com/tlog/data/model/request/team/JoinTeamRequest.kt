package com.tlog.data.model.request.team

data class JoinTeamRequest(
    val inviteCode: String,
    val userId: String
)
