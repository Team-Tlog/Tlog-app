package com.tlog.data.dto.user

import com.tlog.data.dto.tbti.TbtiDescriptionDto

data class UserDto(
    val username: String,
    val snsId: String?,
    val profileImageUrl: String?,
    val defaultRewardPhrase: String,
    val userRewards: List<RewardDto>,
    val tbtiDescription: TbtiDescriptionDto
)
