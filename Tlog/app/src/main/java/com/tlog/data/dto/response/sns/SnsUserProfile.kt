package com.tlog.data.dto.response.sns

import com.tlog.data.dto.response.base.BaseListPage

data class SnsUserProfile(
    val username: String,
    val profileImageUrl: String?,
    val snsDescription: String?,
    val postCount: Int,
    val followerCount: Int,
    val followingCount: Int,
    val posts: BaseListPage<List<SnsPostPreview>>
)
