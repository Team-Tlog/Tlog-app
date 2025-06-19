package com.tlog.api

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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SnsApi {
    // SNS 유저 명 업데이트
    @PATCH("/api/users/snsId")
    suspend fun updateSnsId(
        @Body request: UpdateSnsIdRequest
    ): BaseResponse<Unit>

    // 팔로잉 사용자들의 최근 게시물 조회
    @GET("/api/posts")
    suspend fun getFollowingPostList(
        @Query("lastPostId") lastPostId: String? = null,
        @Query("size") size: Int
    ): BaseListResponse<List<SnsPost>>

    // 유저 프로필 정보 (마이페이지 SNS)
    @GET("/api/sns/user/{userId}/profile")
    suspend fun getUserProfile(
        @Path("userId") userId: String
    ): BaseResponse<SnsUserProfile>

    // SNS 프로필 한 줄 설명글 변경 (UI 없어서 실제로 사용하진 못함)
    @POST("/api/sns/profile/sns-description")
    suspend fun updateSnsDescription(
        @Body request: SnsDescription
    ): BaseResponse<Unit>

    // 게시물 상세 정보 가져오기
    @GET("/api/post/{postId}")
    suspend fun getPost(
        @Path("postId") postId: String
    ): BaseResponse<SnsPost>

    // SNS 검색 기능
    @GET("/api/search/post/by-destination-and-content")
    suspend fun searchPost(
        @Query("query") query: String,
        @Query("size") size: Int,
        @Query("lastPostId") lastPostId: String? = null,
    ): BaseListResponse<List<SnsPostPreview>>

    // 게시물에 댓글 작성
    @POST("/api/post/{postId}/reply")
    suspend fun createComment(
        @Path("postId") postId: String,
        @Body request: CreateCommentRequest
    ): BaseResponse<Comment>

    // 팔로잉 목록
    @GET("/api/follow/following/{userId}")
    suspend fun getFollowingList(
        @Path("userId") userId: String
    ): BaseResponse<List<SnsUser>>

    // 팔로우 걸기 취소
    @POST("/api/follow")
    suspend fun followUser(
        @Body request: FollowRequest
    ): BaseResponse<StatusMessage>










    // -- 연결 X --
    // SNS 댓글
    // 댓글에 대댓글 작성
    @POST("/api/reply/{replyId}/reply")
    suspend fun createReply(
        @Body author: String,
        @Body content: String
    ): BaseResponse<Comment>



    // 댓글의 대댓글 조회
    @GET("/api/reply/{replyId}/replys")
    suspend fun getReplyList(
        @Path("replyId") replyId: String,
        @Query("lastReplyId") lastReplyId: String? = null,
        @Query("size") size: Int
    ): BaseResponse<List<Comment>>

    // 댓글 조회
    @GET("/api/post/{postId}/replys")
    suspend fun getCommentList(
        @Path("postId") postId: String,
        @Query("lastReplyId") lastReplyId: String? = null,
        @Query("size") size: Int
    ): BaseResponse<List<Comment>>


    // SNS 코스 리뷰 (게시물)
    @POST("/api/post")
    suspend fun createPost(
        @Body author: String,
        @Body courseId: String,
        @Body content: String,
        @Body imageUrls: List<String>
    ): BaseResponse<SnsPost>

    // 사용자의 코스 리뷰 미리보기 정보
    @GET("/api/user/{userId}/posts/preview")
    suspend fun getUserPostPreview(
        @Path("userId") userId: String,
        @Body page: Int,
        @Body size: Int,
        @Body sort: List<String>
    ): BaseListResponse<List<SnsPostPreview>>





    @GET("/api/follow/follower/{userId}")
    suspend fun getFollowerList(
        @Path("userId") userId: String,
        @Body page: Int,
        @Body size: Int,
        @Body sort: List<String>
    ): BaseListResponse<List<SnsUser>>
}

