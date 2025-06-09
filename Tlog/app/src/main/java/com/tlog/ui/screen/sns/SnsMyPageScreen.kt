package com.tlog.ui.screen.sns

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.tlog.api.SnsPostPreview
import com.tlog.api.SnsUserProfile
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.sns.SnsMyPageViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.TextStyle
import com.tlog.ui.theme.MainFont

@Composable
fun SnsMyPageScreen(
    viewModel: SnsMyPageViewModel = hiltViewModel(),
    userId: String,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.getUserProfile(userId)
    }

    val userProfile = viewModel.userProfileInfo.collectAsState().value

    if (userProfile != null) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 27.dp, end = 14.dp, bottom = 10.dp, top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = userProfile.username,
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

            ProfileSection(userProfile)

            Spacer(modifier = Modifier.height(15.dp))

            ActionButtons(
                isTowButton = userId == viewModel.userId.value,
            )

            Spacer(modifier = Modifier.height(32.dp))

            PostsGrid(
                postList = userProfile.posts.content,
                onClick = { postId ->
                    navController.navigate("snsPostDetail/$postId")
                }
            )
        }
    }
    else {
        // 로딩 중
    }
}

@Composable
fun ProfileSection(
    userProfile: SnsUserProfile
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 41.dp, top = 10.dp, bottom = 10.dp)
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

                if (userProfile.profileImageUrl == null || userProfile.profileImageUrl == "") {
                    Image(
                        painter = painterResource(id = R.drawable.test_logo),
                        contentDescription = "Profile Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                else {
                    AsyncImage(
                        model = userProfile.profileImageUrl,
                        contentDescription = "Profile Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.width(38.dp))

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
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
            modifier = Modifier.padding(top = 17.dp)
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
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ActionButtons(
    isTowButton: Boolean = true,
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
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "프로필 편집",
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = MainFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(vertical = 15.dp)
                )
            }

            Button(
                onClick = { rightButtonClick() },
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "프로필 공유",
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = MainFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(vertical = 15.dp)
                )
            }
        }
        else {
            Button(
                onClick = { buttonClick() },
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "팔로우",
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = MainFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(vertical = 15.dp)
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
        items(postList) { post ->
            if (post.previewImageUrl != null && post.previewImageUrl != "") { // 정상적인 상황에선 있을 수 없음 무조건 이미지 url이 정상 하지만 테스트를 위해
                AsyncImage(
                    model = post.previewImageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clickable {
                            onClick(post.postId)
                        }
                )
            }
            else {
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(Color.Gray)
                ) {
                    Text(
                        text = "XXX",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}