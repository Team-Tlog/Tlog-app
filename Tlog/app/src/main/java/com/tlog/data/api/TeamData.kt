package com.tlog.data.api


data class CreateTeamRequest(
    val name: String,
    val creator: String
)

data class JoinTeamRequest(
    val inviteCode: String,
    val userId: String
)

data class UpdateTbtiResponse(
    val tbtiString: String,
    val imageUrl: String,
    val secondName: String,
    val description: String
)

data class TeamCreateResponse(
    val teamId: String,
    val chatRoomId: Int
)
