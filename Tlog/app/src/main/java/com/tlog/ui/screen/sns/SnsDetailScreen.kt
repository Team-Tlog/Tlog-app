package com.tlog.ui.screen.sns

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.data.model.sns.Comment
import com.tlog.ui.component.sns.PostAuthorInfo
import com.tlog.ui.component.sns.PostContentAndInteractions
import com.tlog.ui.component.sns.PostImage
import com.tlog.ui.navigation.Screen
import com.tlog.ui.style.Body1Regular
import com.tlog.ui.theme.MainFont
import com.tlog.ui.theme.TextSubdued
import com.tlog.viewmodel.sns.SnsDetailViewModel
import com.tlog.viewmodel.sns.SnsDetailViewModel.UiEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SnsDetailScreen(
    viewModel: SnsDetailViewModel = hiltViewModel(),
    navController: NavController,
    postId: String,
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getPostDetail(postId)

        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.target) {
                        if (event.clearBackStack) popUpTo(Screen.Main) { inclusive = false }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
                is UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    val followingList = viewModel.followingList.collectAsState().value
    val post = viewModel.post.collectAsState()

    Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                post.value?.let { post ->
                    PostAuthorInfo(
                        userId = post.authorName,
                        userProfileImageUrl = post.authorProfileImageUrl,
                        isFollowing = followingList.contains(post.authorId),
                        isMyPost = viewModel.userId == post.authorId,
                        onFollowToggle = {
                            viewModel.followUser(viewModel.post.value!!.authorId)
                        },
                        clickUser = {
                            viewModel.navToSnsMyPage(viewModel.post.value!!.authorId)
                        }
                    )

                    PostImage(
                        images = post.contentImageUrls
                    )

                    Spacer(modifier = Modifier.height(26.dp))

                    PostContentAndInteractions(
                        content = post.content,
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

                    post.replies.forEach { comment ->
                        CommentItem(
                            comment = comment,
                            userClick = { targetUserId ->
                                viewModel.navToSnsMyPage(targetUserId)
                            }
                        )
                    }
                }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 14.dp, bottom = 32.dp, start = 8.dp, end = 8.dp)
            ) {
                CommentField(
                    value = viewModel.comment.value,
                    onValueChange = { comment ->
                        viewModel.updateComment(comment)
                    },
                    sendClick = {
                        if (viewModel.comment.value.isNotEmpty()) {
                            viewModel.addComment()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CommentItem(
    comment: Comment,
    userClick: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = (23.5).dp, end = 23.5.dp, bottom = 26.dp)
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
                .clickable {
                    userClick(comment.authorId)
                }
        )

        Spacer(modifier = Modifier.width(17.dp))

        Column {
            Text(
                text = comment.authorName,
                style = Body1Regular,
                modifier = Modifier
                    .clickable {
                        userClick(comment.authorId)
                    }
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

@Composable
fun CommentField(
    value: String,
    onValueChange: (String) -> Unit,
    sendClick: () -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFBFBFB))
            .clip(RoundedCornerShape(15.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = "댓글 작성하기",
                    fontSize = 15.sp,
                    style = Body1Regular.copy(color = TextSubdued),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            textStyle = Body1Regular,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFBFBFB),
                unfocusedContainerColor = Color(0xFFFBFBFB),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            modifier = Modifier
                .weight(1f)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_lock),
            contentDescription = "", // 아직 뭔지 몰라용
            tint = Color.Unspecified,
            modifier = Modifier
                .size(20.dp)
        )

        Spacer(modifier = Modifier.width(13.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_comment_send),
            contentDescription = "댓글 작성하기",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    sendClick()
                }
        )

        Spacer(modifier = Modifier.width(15.dp))

    }
}
