package com.tlog.domain.mapper

import com.tlog.data.dto.response.sns.SnsPostDto
import com.tlog.data.dto.response.sns.SnsPostPreviewDto
import com.tlog.data.dto.response.sns.SnsUserDto
import com.tlog.data.dto.response.sns.SnsUserProfileDto
import com.tlog.data.dto.sns.CommentDto
import com.tlog.domain.model.sns.Comment
import com.tlog.domain.model.sns.SnsPost
import com.tlog.domain.model.sns.SnsPostPreview
import com.tlog.domain.model.sns.SnsProfile
import com.tlog.domain.model.sns.SnsUser

fun CommentDto.toDomain(): Comment {
    return Comment(
        userId = authorId,
        userName = authorName,
        comment = content,
        userProfileImageUrl = authorProfileImageUrl ?: ""
    )
}

fun SnsPostDto.toDomain(): SnsPost {
    return SnsPost(
        id = postId,
        postLikeCount = postLikeCount,
        authorId = authorId,
        authorName = authorName,
        authorProfileImageUrl = authorProfileImageUrl ?: "",
        contentImageUrls = contentImageUrls,
        content = content,
        comments = replies.map { it.toDomain() }
    )
}

fun SnsPostPreviewDto.toDomain(): SnsPostPreview {
    return SnsPostPreview(
        postId = postId,
        previewImageUrl = previewImageUrl
    )
}

fun SnsUserProfileDto.toDomain(): SnsProfile {
    return SnsProfile(
        username = username,
        profileImageUrl = profileImageUrl,
        snsDescription = snsDescription,
        postCount = postCount,
        followerCount = followerCount,
        followingCount = followingCount,
        posts = posts.content.map { it.toDomain() }
    )
}

fun SnsUserDto.toDomain(): SnsUser {
    return SnsUser(
        uuid = uuid,
        snsName = snsName,
        profileImageUrl = profileImageUrl ?: ""
    )
}
