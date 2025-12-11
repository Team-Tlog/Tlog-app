package com.tlog.domain.model.team

data class Member(
    val userId: String,
    val profileImageUrl: String,
    val name: String,
    val tbtiString: String,
    val isLeader: Boolean
)