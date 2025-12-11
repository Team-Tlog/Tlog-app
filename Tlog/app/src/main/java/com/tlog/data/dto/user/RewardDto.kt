package com.tlog.data.dto.user

data class RewardDto(
    val rewardId: Long,
    val name: String,
    val description: String,
    val iconImageUrl: String,
    val isDefaultReward: Boolean
)
