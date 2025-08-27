package com.tlog.data.model.user

import com.tlog.data.model.share.TbtiDescription

data class User(
    val username: String,
    val snsId: String,
    val profileImageUrl: String?,
    val defaultRewardPhrase: String,
    val userRewards: List<Reward>,
    val tbtiDescription: TbtiDescription
)