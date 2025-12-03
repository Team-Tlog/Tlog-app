package com.tlog.data.repository

import com.tlog.api.SnsApi
import com.tlog.data.dto.response.base.BaseListResponse
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.response.sns.CommentRequest
import com.tlog.data.dto.request.sns.FollowRequest
import com.tlog.data.dto.request.sns.ReportRequest
import com.tlog.data.dto.request.sns.SnsDescriptionBody
import com.tlog.data.dto.response.sns.SnsPost
import com.tlog.data.dto.response.sns.SnsPostPreview
import com.tlog.data.dto.response.sns.SnsUser
import com.tlog.data.dto.response.sns.SnsUserProfile
import com.tlog.data.dto.response.sns.StatusMessageResponse
import com.tlog.data.dto.request.sns.UpdateSnsIdRequest
import com.tlog.data.dto.sns.CommentDto
import javax.inject.Inject

class SnsRepository @Inject constructor(
    private val retrofitInstance: SnsApi,
) {
    suspend fun updateSnsId(snsId: String): BaseResponse<Unit> {
        return retrofitInstance.updateSnsId(UpdateSnsIdRequest(snsId))
    }

    suspend fun getFollowingPostList(
        lastPostId: String? = null,
        size: Int,
    ): BaseListResponse<List<SnsPost>> {
        return retrofitInstance.getFollowingPostList(lastPostId, size)
    }

    suspend fun getUserProfile(
        userId: String
    ): BaseResponse<SnsUserProfile> {
        return retrofitInstance.getUserProfile(userId)
    }

    suspend fun updateSnsDescription(
        description: String
    ): BaseResponse<Unit> {
        return retrofitInstance.updateSnsDescription(SnsDescriptionBody(description))
    }

    suspend fun getPost(
        postId: String
    ): BaseResponse<SnsPost> {
        return retrofitInstance.getPost(postId)
    }

    suspend fun searchPost(
        query: String,
        lastPostId: String? = null,
        size: Int,
    ): BaseListResponse<List<SnsPostPreview>> {
        return retrofitInstance.searchPost(query = query, size = size, lastPostId = lastPostId)
    }

    suspend fun createComment(
        postId: String,
        author: String,
        content: String
    ): BaseResponse<CommentDto>{
        return retrofitInstance.addComment(postId, CommentRequest(author = author, content = content))
    }

    suspend fun getFollowingList(userId: String): BaseResponse<List<SnsUser>> {
        return retrofitInstance.getFollowingList(userId)
    }

    suspend fun followUser(userId: String, toUserId: String): BaseResponse<StatusMessageResponse> {
        return retrofitInstance.followUser(FollowRequest(from_userId = userId, to_userId = toUserId))
    }

    suspend fun postLikeToggle(postId: String): BaseResponse<Unit> {
        return retrofitInstance.postLikeToggle(postId)
    }

    suspend fun postReport(postId: String): BaseResponse<Unit> {
        return retrofitInstance.postReport(ReportRequest(postId))
    }
}
