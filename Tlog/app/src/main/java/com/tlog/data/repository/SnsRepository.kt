package com.tlog.data.repository

import com.tlog.api.SnsApi
import com.tlog.data.api.BaseListResponse
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.CreateCommentRequest
import com.tlog.data.api.FollowRequest
import com.tlog.data.api.SnsDescription
import com.tlog.data.api.SnsPost
import com.tlog.data.api.SnsPostPreview
import com.tlog.data.api.SnsUser
import com.tlog.data.api.SnsUserProfile
import com.tlog.data.api.StatusMessage
import com.tlog.data.api.UpdateSnsIdRequest
import com.tlog.data.model.sns.Comment
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
        return retrofitInstance.updateSnsDescription(SnsDescription(description))
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
    ): BaseResponse<Comment>{
        return retrofitInstance.createComment(postId, CreateCommentRequest(author = author, content = content))
    }

    suspend fun getFollowingList(userId: String): BaseResponse<List<SnsUser>> {
        return retrofitInstance.getFollowingList(userId)
    }

    suspend fun followUser(userId: String, toUserId: String): BaseResponse<StatusMessage> {
        return retrofitInstance.followUser(FollowRequest(from_userId = userId, to_userId = toUserId))
    }
}