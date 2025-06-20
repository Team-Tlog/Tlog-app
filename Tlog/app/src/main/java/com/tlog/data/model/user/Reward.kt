package com.tlog.data.model.user

data class Reward(
    val rewardId: String,
    val name: String,
    val description: String,
    val iconImageUrl: String,
    val isDefaultReward: Boolean
)