package com.tlog.data.repository

import com.tlog.api.SnsApi
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.response.sns.CommentRequest
import com.tlog.data.dto.request.sns.FollowRequest
import com.tlog.data.dto.request.sns.ReportRequest
import com.tlog.data.dto.request.sns.SnsDescriptionBody
import com.tlog.data.dto.response.sns.StatusMessageResponse
import com.tlog.data.dto.request.sns.UpdateSnsIdRequest
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.sns.Comment
import com.tlog.domain.model.sns.SnsPost
import com.tlog.domain.model.sns.SnsPostPreview
import com.tlog.domain.model.sns.SnsProfile
import com.tlog.domain.model.sns.SnsUser
import javax.inject.Inject

class SnsRepository @Inject constructor(
    private val retrofitInstance: SnsApi,
) {
    suspend fun updateSnsId(snsId: String): BaseResponse<Unit> {
        return retrofitInstance.updateSnsId(UpdateSnsIdRequest(snsId))
    }

    suspend fun getFollowingPostList(
        lastPostId: String? = null,
        size: Int
    ): List<SnsPost> {
        return retrofitInstance.getFollowingPostList(lastPostId, size).data.content.map {
            it.toDomain()
        }
    }

    suspend fun getUserProfile(
        userId: String
    ): SnsProfile {
        return retrofitInstance.getUserProfile(userId).data.toDomain()
    }

    suspend fun updateSnsDescription(description: String): BaseResponse<Unit> {
        return retrofitInstance.updateSnsDescription(SnsDescriptionBody(description))
    }

    suspend fun getPost(postId: String): SnsPost {
        return retrofitInstance.getPost(postId).data.toDomain()
    }

    suspend fun searchPost(
        query: String,
        lastPostId: String? = null,
        size: Int,
    ): Pair<List<SnsPostPreview>, String> {
        val response = retrofitInstance.searchPost(query = query, size = size, lastPostId = lastPostId)

        return response.data.content.map { it.toDomain() } to response.data.content.last().postId
    }

    suspend fun createComment(
        postId: String,
        author: String,
        content: String
    ): Comment {
        return retrofitInstance.addComment(postId, CommentRequest(author = author, content = content)).data.toDomain()
    }

    suspend fun getFollowingList(userId: String): List<SnsUser> {
        return retrofitInstance.getFollowingList(userId).data.map {
            it.toDomain()
        }
    }

    // 일단 사용 안해서 매핑 제외
    suspend fun followUser(userId: String, toUserId: String): BaseResponse<StatusMessageResponse> {
        return retrofitInstance.followUser(FollowRequest(from_userId = userId, to_userId = toUserId))
    }

    suspend fun postLikeToggle(postId: String) {
        retrofitInstance.postLikeToggle(postId)
    }

    suspend fun postReport(postId: String) {
        retrofitInstance.postReport(ReportRequest(postId))
    }
}
