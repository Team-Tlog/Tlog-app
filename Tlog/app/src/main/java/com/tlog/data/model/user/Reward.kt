package com.tlog.data.model.user

data class Reward(
    val rewardId: Long,
    val name: String,
    val description: String,
    val iconImageUrl: String,
    val isDefaultReward: Boolean
)