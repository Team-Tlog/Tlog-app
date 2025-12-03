package com.tlog.data.dto.team

data class Member(
    val userId: String,
    val profileImageUrl: String,
    val name: String,
    val tbtiString: String,
    val isLeader: Boolean
)
