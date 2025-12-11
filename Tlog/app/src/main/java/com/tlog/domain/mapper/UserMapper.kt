package com.tlog.domain.mapper

import com.tlog.data.dto.user.RewardDto
import com.tlog.data.dto.user.UserDto
import com.tlog.domain.model.mypage.Reward
import com.tlog.domain.model.share.User

fun RewardDto.toDomain(): Reward {
    return Reward(
        id = rewardId.toString(),
        name = name,
        description = description,
        iconImageUrl = iconImageUrl,
        isDefaultReward = isDefaultReward
    )
}

fun UserDto.toDomain(): User {
    return User(
        name = username,
        snsId = snsId,
        profileImageUrl = profileImageUrl ?: "",
        defaultRewardPhrase = defaultRewardPhrase,
        userRewards = userRewards.map { it.toDomain() },
        tbtiDescription = tbtiDescription.toDomain()
    )
}
