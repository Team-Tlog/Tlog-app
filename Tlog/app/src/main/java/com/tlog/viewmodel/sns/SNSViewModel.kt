package com.tlog.viewmodel.sns

// ViewModel 및 데이터 모델
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.tlog.data.model.sns.Comment
import com.tlog.data.model.sns.PostData
import com.tlog.data.model.sns.SNSIdRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SNSViewModel : ViewModel() {
    // 현재 로그인한 사용자 정보
    private val _currentUser = MutableStateFlow(SNSIdRequest("사용자명", false))
    val currentUser: StateFlow<SNSIdRequest> = _currentUser

    // 게시물 목록
    private val _posts = MutableStateFlow<List<PostData>>(emptyList())
    val posts: StateFlow<List<PostData>> = _posts

    // 게시물별 댓글 관리 (게시물 ID를 키로 사용)
    private val commentsMap = mutableMapOf<String, MutableStateFlow<List<Comment>>>()
    private val hasMoreCommentsMap = mutableMapOf<String, MutableStateFlow<Boolean>>()

    init {
        // 초기 데이터 로드
        loadPosts()
    }

    private fun loadPosts() {
        // 실제 구현에서는 API 호출 등을 통해 데이터를 가져옵니다
        val samplePosts = listOf(
            PostData(
                id = "post1",
                userId = "사용자1",
                likesCount = 15,
                isLiked = false,
                isUserFollowing = false,
                commentsPreview = listOf(
                    Comment("user1", "첫 번째 게시물의 댓글입니다."),
                    Comment("user2", "멋진 게시물이네요!")
                ),
                courseTitles = listOf("코스1", "코스2", "코스3", "코스4", "코스5","코스6", "코스7") // 예시
            ),
            PostData(
                id = "post2",
                userId = "사용자2",
                likesCount = 42,
                isLiked = false,
                isUserFollowing = false,
                commentsPreview = listOf(
                    Comment("user3", "두 번째 게시물에 댓글 남깁니다!"),
                    Comment("user4", "좋은 정보 감사합니다.")
                ),
                courseTitles = listOf("코스1", "코스2", "코스3", "코스4", "코스5") // 예시
            ),
            PostData(
                id = "post3",
                userId = "사용자3",
                likesCount = 7,
                isLiked = false,
                isUserFollowing = false,
                commentsPreview = listOf(
                    Comment("user5", "세 번째 게시물 댓글입니다."),
                    Comment("user6", "흥미로운 내용이네요.")
                ),
                courseTitles = listOf("코스1", "코스2", "코스3") // 예시
            )
        )

        _posts.value = samplePosts

        // 각 게시물에 대한 댓글 상태 초기화
        samplePosts.forEach { post ->
            commentsMap[post.id] = MutableStateFlow(post.commentsPreview)
            hasMoreCommentsMap[post.id] = MutableStateFlow(true) // 더 볼 댓글이 있다고 가정
        }
    }

    // 특정 게시물의 댓글 가져오기
    fun getDisplayedComments(postId: String): StateFlow<List<Comment>> {
        return commentsMap[postId] ?: MutableStateFlow(emptyList())
    }

    // 특정 게시물에 더 볼 댓글이 있는지 확인
    fun hasMoreComments(postId: String): StateFlow<Boolean> {
        return hasMoreCommentsMap[postId] ?: MutableStateFlow(false)
    }

    // 좋아요 토글
    fun toggleLike(postId: String) {
        _posts.value = _posts.value.map { post ->
            if (post.id == postId) {
                val newLikeStatus = !post.isLiked
                val newLikesCount = if (newLikeStatus) post.likesCount + 1 else post.likesCount - 1
                post.copy(isLiked = newLikeStatus, likesCount = newLikesCount)
            } else {
                post
            }
        }
    }

    // 팔로우 토글
    fun toggleFollow(userId: String) {
        _posts.value = _posts.value.map { post ->
            if (post.userId == userId) {
                post.copy(isUserFollowing = !post.isUserFollowing)
            } else {
                post
            }
        }
    }

    // 모든 댓글 로드
    fun loadAllComments(postId: String) {
        val currentComments = commentsMap[postId]?.value ?: emptyList()

        // 실제 구현에서는 API 호출 등을 통해 추가 댓글 로드
        val additionalComments = listOf(
            Comment("user7", "추가 댓글 1"),
            Comment("user8", "추가 댓글 2"),
            Comment("user9", "추가 댓글 3")
        )

        commentsMap[postId]?.value = currentComments + additionalComments       //기존 댓글과 추가 댓글리스트로 합침
        hasMoreCommentsMap[postId]?.value = false // 모든 댓글을 로드했으므로 더 이상 없음
    }

}

//// 데이터 클래스들
//data class User(
//    val id: String,
//    val isFollowing: Boolean
//)

//data class Post(
//    val id: String,
//    val userId: String,
//    val likesCount: Int,
//    val isLiked: Boolean,
//    val isUserFollowing: Boolean,
//    val commentsPreview: List<Comment> = emptyList(),
//    val courseTitles: List<String> = emptyList() // 추가된 필드
//)

//data class Comment(
//    val userId: String,
//    val content: String
//)