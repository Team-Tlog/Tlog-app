package com.tlog.ui.screen.sns

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.api.Comment
import com.tlog.ui.component.SNS.PostAuthorInfo
import com.tlog.ui.component.SNS.PostContentAndInteractions
import com.tlog.ui.component.SNS.PostImage
import com.tlog.ui.style.Body1Regular
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.sns.SnsDetailViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SnsDetailScreen(
    viewModel: SnsDetailViewModel = hiltViewModel(),
    postId: String,
) {
    LaunchedEffect(Unit) {
        viewModel.getPostDetail(postId)
    }

    if (viewModel.post.value != null) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                PostAuthorInfo(
                    userId = viewModel.post.value!!.authorName,
                    isUserFollowing = false,
                    isMyPost = viewModel.userId == viewModel.post.value!!.authorId,
                    onFollowToggle = { },
                )

                PostImage(
                    images = viewModel.post.value!!.contentImageUrls
                )

                Spacer(modifier = Modifier.height(26.dp))

                PostContentAndInteractions(
                    content = viewModel.post.value!!.content,
                    isLiked = false,
                    onLikeClick = {
                        // 좋아요
                    },
                    onShareClick = {
                        Log.d("공유 아이콘", "공유 아이콘")
                    },
                    onReportClick = {
                        Log.d("신고하기", "신고하기")
                    },
                    isSingleLine = false
                )

                Spacer(modifier = Modifier.height(26.dp))

                viewModel.post.value!!.replies.forEach { comment ->
                    CommentItem(comment)
                }
            }


        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = comment.authorProfileImageUrl,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.destination_img),
            error = painterResource(id = R.drawable.destination_img),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(17.dp))

        Column {
            Text(
                text = comment.authorName,
                style = Body1Regular
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = comment.content,
                style = TextStyle(
                    fontFamily = MainFont,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp
                )
            )
        }
    }
}
