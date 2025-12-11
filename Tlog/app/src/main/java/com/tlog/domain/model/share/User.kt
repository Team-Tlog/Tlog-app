package com.tlog.domain.model.share

import com.tlog.domain.model.mypage.Reward

data class User(
    val name: String,
    val snsId: String?,
    val profileImageUrl: String,
    val defaultRewardPhrase: String,
    val userRewards: List<Reward>,
    val tbtiDescription: TbtiDescription
)
