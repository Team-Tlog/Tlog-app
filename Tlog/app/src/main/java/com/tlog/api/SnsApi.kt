package com.tlog.api

import com.tlog.data.api.BaseListPage
import com.tlog.data.api.BaseListResponse
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.MinimalListPage
import com.tlog.data.model.travel.Pageable
import com.tlog.data.model.travel.Sort
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







    // -- 연결 X --
    // SNS 댓글
    // 댓글에 대댓글 작성
    @POST("/api/reply/{replyId}/reply")
    suspend fun createReply(
        @Body author: String,
        @Body content: String
    ): BaseResponse<Comment>

    // 게시물에 댓글 작성
    @POST("/api/post/{postId}/reply")
    suspend fun createComment(
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

    @GET("/api/post/{postId}")
    suspend fun getPost(
        @Path("postId") postId: String
    ): BaseResponse<SnsPost>

    // 팔로우
    @POST("/api/follow")
    suspend fun followUser(
        @Body from_userId: String,
        @Body to_userId: String
    ): BaseResponse<StatusMessage>

    @GET("/api/follow/following/{userId}")
    suspend fun getFollowingList(
        @Path("userId") userId: String,
        @Body page: Int,
        @Body size: Int,
        @Body sort: List<String>
    ): BaseListResponse<List<SnsUser>>

    @GET("/api/follow/follower/{userId}")
    suspend fun getFollowerList(
        @Path("userId") userId: String,
        @Body page: Int,
        @Body size: Int,
        @Body sort: List<String>
    ): BaseListResponse<List<SnsUser>>
}

data class UpdateSnsIdRequest(
    val snsId: String
)

data class Comment(
    val replyId: String,
    val content: String,
    val nestedReplyCount: Int,
    val authorId: String,
    val authorName: String,
    val authorProfileImageUrl: String
)

data class SnsPost(
    val postId: String,
    val postLikeCount: Int,
    val postLinkCode: String,
    val courseId: String,
    val courseDistrics: List<String>,
    val authorId: String,
    val authorName: String,
    val authorProfileImageUrl: String,
    val contentImageUrls: List<String>,
    val content: String,
    val replies: List<Comment>
)

data class SnsPostPreview(
    val postId: String,
    val previewImageUrl: String
)

data class StatusMessage(
    val status: Boolean,
    val message: String
)

data class SnsUser(
    val uuid: String,
    val name: String
)


data class SnsUserProfile(
    val username: String,
    val profileImageUrl: String?,
    val snsDescription: String?,
    val postCount: Int,
    val followerCount: Int,
    val followingCount: Int,
    val posts: BaseListPage<List<SnsPostPreview>>
)
