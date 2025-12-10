package com.tlog.domain.model.sns

data class SnsProfile(
    val username: String,
    val profileImageUrl: String?,
    val snsDescription: String?,
    val postCount: Int,
    val followerCount: Int,
    val followingCount: Int,
    val posts: List<SnsPostPreview>
)
