package com.tlog.domain.model.mypage

data class Reward(
    val id: String,
    val name: String,
    val description: String,
    val iconImageUrl: String,
    val isDefaultReward: Boolean
)
