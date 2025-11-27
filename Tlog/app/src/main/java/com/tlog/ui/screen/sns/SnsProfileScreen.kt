package com.tlog.ui.screen.sns

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.sns.SnsMyPageViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import com.tlog.data.model.response.sns.SnsPostPreview
import com.tlog.data.model.response.sns.SnsUserProfile
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.base.BaseViewModel.UiEvent
import com.tlog.ui.theme.DefaultImage

@Composable
fun SnsProfileScreen(
    viewModel: SnsMyPageViewModel = hiltViewModel(),
    userId: String,
    navController: NavController
) {
    val context = LocalContext.current

    val followingList = viewModel.followingList.collectAsState().value
    val userProfile by viewModel.userProfileInfo.collectAsState()

    val nowUserProfile = userProfile

    LaunchedEffect(Unit) {
        viewModel.getUserProfile(userId)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.target) {
                        if (event.clearBackStack) popUpTo(navController.graph.id) { inclusive = true }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
                is UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is UiEvent.PopBackStack -> Unit
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(Color.White)
    ) {
        if (nowUserProfile != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 27.dp, end = 14.dp, bottom = 10.dp, top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = nowUserProfile.username,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Row {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search"
                        )
                    }

                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = "Notifications"
                        )
                    }
                }
            }

            ProfileSection(nowUserProfile)

            Spacer(modifier = Modifier.height(15.dp))

            if (viewModel.getIsMyProfile(userId)) {
                ActionButtons()
            } else {
                ActionButtons(
                    isTowButton = false,
                    isFollowing = followingList.contains(userId),
                    buttonClick = {
                        viewModel.followUser(userId)
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            PostsGrid(
                postList = nowUserProfile.posts.content,
                onClick = { postId ->
                    viewModel.navToSnsPostDetail(postId)
                }
            )
        }
    }
}

@Composable
fun ProfileSection(
    userProfile: SnsUserProfile
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 10.dp, bottom = 10.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE6D08A))
            ) {
                AsyncImage(
                    model = userProfile.profileImageUrl,
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = DefaultImage)
                )
            }

            Spacer(modifier = Modifier.width(38.dp))

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                StatItem(userProfile.posts.content.size.toString(), "게시글")
                StatItem(userProfile.followerCount.toString(), "팔로워")
                StatItem(userProfile.followingCount.toString(), "팔로잉")
            }
        }


        Text(
            text = userProfile.snsDescription ?: "나에 대한 설명글을 입력하세요",
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 17.dp, start = 9.dp)
        )
    }
}

@Composable
fun StatItem(count: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count,
            style = Body1Bold
        )

        Spacer(modifier = Modifier.height(11.dp))

        Text(
            text = label,
            style = TextStyle(
                fontFamily = MainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        )
    }
}

@Composable
fun ActionButtons(
    isTowButton: Boolean = true,
    isFollowing: Boolean = false,
    leftButtonClick: () -> Unit = {},
    rightButtonClick: () -> Unit = {},
    buttonClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (isTowButton) {
            Button(
                onClick = { leftButtonClick() },
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "프로필 편집",
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = MainFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    ),
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }

            Button(
                onClick = { rightButtonClick() },
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "프로필 공유",
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = MainFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    ),
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
        else {
            Button(
                onClick = { buttonClick() },
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = if (isFollowing) "팔로잉" else "팔로우",
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = MainFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    ),
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
    }
}

@Composable
fun PostsGrid(
    postList: List<SnsPostPreview>,
    onClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth()
    ) {
        postList.forEach { post ->
            item {
                AsyncImage(
                    model = post.previewImageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = DefaultImage),
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clickable {
                            onClick(post.postId)
                        }
                )
            }
        }
    }
}