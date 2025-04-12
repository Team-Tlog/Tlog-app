package com.tlog.ui.component.SNS

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tlog.R
import com.tlog.data.model.sns.PostData
import com.tlog.viewmodel.sns.SNSViewModel

@Composable
fun PostItem(post: PostData, viewModel: SNSViewModel) {
    val displayedComments by viewModel.getDisplayedComments(post.id).collectAsState()
    val hasMoreComments by viewModel.hasMoreComments(post.id).collectAsState()
    var selectedImageIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // 게시물 작성자 정보
        PostAuthorInfo(
            userId = post.userId,
            isUserFollowing = post.isUserFollowing,
            onFollowToggle = { userId -> viewModel.toggleFollow(userId) },
        )

        PostImage(
            images = post.images,
            iconResId = R.drawable.ic_add_circle,
            onPageChanged = { newIndex ->
                selectedImageIndex = newIndex
            }
        )

        StepCourseBar(
            courseTitles = post.courseTitles,
            activeStepIndex = selectedImageIndex
        )

        ViewCourseButton(
            onClick = {
                Log.d("코스보러가기", "코스 보러가기")
            },
            buttonText = "코스 확인하기"
        )

        // 게시물 내용 추가
        PostContentAndInteractions(
            content = post.content,
            isLiked = post.isLiked,
            onLikeClick = { viewModel.toggleLike(post.id) },
            onShareClick = {
                Log.d("공유 아이콘", "공유 아이콘")
                // 추가 공유 로직
            },
            onReportClick = {
                Log.d("신고하기", "신고하기")
                // 추가 신고 로직
            }
            // 기본 패딩 사용 - 파라미터 생략
        )

        // 현재 표시된 댓글 목록
        displayedComments.forEach { comment ->
            CommentItem(comment = comment)
        }

    }
}