package com.tlog.ui.component.SNS

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.data.model.sns.PostData
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.style.Body2Regular
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.theme.Essential
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.sns.SNSViewModel

@Composable
fun PostItem(post: PostData, viewModel: SNSViewModel) {
    val displayedComments by viewModel.getDisplayedComments(post.id).collectAsState()
    val hasMoreComments by viewModel.hasMoreComments(post.id).collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        // 게시물 작성자 정보
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 프로필 사진
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 사용자 이름
            Text(
                text = post.userId,
                style = Body1Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            // 팔로우 버튼
            Button(
                onClick = { viewModel.toggleFollow(post.userId) },
                modifier = Modifier
                    .width(72.dp)
                    .height(32.dp),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor)
            ) {
                Text(
                    text = if (post.isUserFollowing) "팔로잉" else "팔로우",
                    fontFamily = MainFont,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )
            }
        }

        // 게시물 사진
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 코스바
        Column(modifier = Modifier.padding(16.dp)) {
            StepCourseBar(courseTitles = post.courseTitles) // 사용자가 선택한 코스 수로 변경
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "코스 보러가기",
                    fontFamily = MainFont,
                    fontWeight = FontWeight.SemiBold,
                    color = Essential,
                    modifier = Modifier.clickable {             //추후 로직 구현
                        Log.d("코스보러가기","코스 보러가기")
                    }

                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_right_arrow),
                    contentDescription = "코스 보러가기",
                    modifier = Modifier.size(16.dp)
                )
            }
        }


        // 좋아요, 공유 ,신고 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 24.dp)
                    .clickable { viewModel.toggleLike(post.id) }
            ) {
                Icon(
                    imageVector = if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "좋아요",
                    modifier = Modifier.size(24.dp),
                    tint = if (post.isLiked) Color.Red else Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "좋아요 ${post.likesCount}",        //동작되는지 확인하기위해 좋아요 카운트 추후 제거하면 될듯
                    fontSize = 14.sp
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 24.dp)
                    .clickable {                                //추후 로직 구현
                        Log.d("공유 아이콘","공유 아이콘")
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = "코스 보러가기",
                    modifier = Modifier.size(24.dp)

                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "공유",
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "신고하기",
                style = Body2Regular,
                modifier = Modifier.clickable {             //추후 로직 구현
                    Log.d("신고하기","신고하기")
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 현재 표시된 댓글 목록
        displayedComments.forEach { comment ->
            CommentItem(comment = comment)
        }

        // 더 볼 댓글이 있을 때만 표시
        if (hasMoreComments) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { viewModel.loadAllComments(post.id) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "더보기",
                        style = Body2Regular,
                        color = Essential
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_right_arrow),
                        contentDescription = "더보기",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}