package com.tlog.ui.component.SNS

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tlog.R
import com.tlog.api.SnsPost
import com.tlog.data.model.sns.PostData
import com.tlog.viewmodel.sns.SnsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostItem(post: SnsPost, viewModel: SnsViewModel) {
    var selectedImageIndex by remember { mutableStateOf(0) }

    // 이미지 페이저 상태 생성
    val pagerState = rememberPagerState(pageCount = { post.contentImageUrls.size })

    // 페이저 상태와 선택된 이미지 인덱스 간의 동기화
    LaunchedEffect(selectedImageIndex) {
        if (pagerState.currentPage != selectedImageIndex) {
            // 애니메이션 없이 바로 페이지 이동
            pagerState.scrollToPage(selectedImageIndex)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        if (selectedImageIndex != pagerState.currentPage) {
            selectedImageIndex = pagerState.currentPage
        }
    }


    Column(modifier = Modifier.fillMaxWidth()) {
        // 게시물 작성자 정보
        PostAuthorInfo(
            userId = post.authorName,
            isUserFollowing = false, // 수정 방안 고안 해볼 것
            onFollowToggle = { userId -> viewModel.toggleFollow(userId) },
        )

        PostImage(
            images = post.contentImageUrls,
            pagerState = pagerState,
            onPageChanged = { newIndex ->
                if (selectedImageIndex != newIndex) {
                    selectedImageIndex = newIndex
                }
            }
        )

//        StepCourseBar(
//            courseTitles = post.courseTitles,
//            activeStepIndex = selectedImageIndex,
//            onStepClicked = { newIndex ->
//                // 클릭한 코스 인덱스가 이미지 범위 내에 있는지 확인
//                if (newIndex < post.images.size && selectedImageIndex != newIndex) {
//                    selectedImageIndex = newIndex
//                }
//            }
//        )

        ViewCourseButton(
            onClick = {
                Log.d("코스보러가기", "코스 보러가기")
            },
            buttonText = "코스 확인하기"
        )

        // 게시물 내용 추가
        PostContentAndInteractions(
            content = post.content,
            isLiked = false, //post.isLiked,
            onLikeClick = {
                // 좋아요
            },
            onShareClick = {
                Log.d("공유 아이콘", "공유 아이콘")
                // 추가 공유 로직
            },
            onReportClick = {
                Log.d("신고하기", "신고하기")
            }
        )

        // 현재 표시된 댓글 목록
        post.replies.forEachIndexed { index, comment ->
            CommentItem(comment = comment)
        }
    }
}