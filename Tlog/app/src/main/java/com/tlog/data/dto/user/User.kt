package com.tlog.data.dto.user

import com.tlog.data.dto.tbti.TbtiDescriptionDto

data class User(
    val username: String,
    val snsId: String?,
    val profileImageUrl: String?,
    val defaultRewardPhrase: String,
    val userRewards: List<Reward>,
    val tbtiDescription: TbtiDescriptionDto
)
