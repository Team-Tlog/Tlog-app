package com.tlog.ui.component.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tlog.data.model.user.User
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.theme.MainFont
import com.tlog.ui.theme.DefaultImage


@Composable
fun UserInfoGroup(
    userInfo: User,
    onImageClick:()-> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, shape = RoundedCornerShape(10))
            .clip(RoundedCornerShape(10))
            .background(Color.White.copy(alpha = 0.8f))
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.8f))
                .padding(
                    top = 31.dp,
                    bottom = 15.dp,
                    start = 15.dp,
                    end = 15.dp
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = userInfo.profileImageUrl,
                    contentDescription = "프로필 사진",
                    contentScale = ContentScale.Crop,
                    error = painterResource(DefaultImage),
                    modifier = Modifier
                        .size(86.dp)
                        .clip(RoundedCornerShape(50))
                        .clickable{onImageClick()}
                )

                Spacer(modifier = Modifier.width(25.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = userInfo.snsId ?: "SNS Id 없음", // SNS 아이디 만들기?
                            style = Body1Bold,
                            color = if (userInfo.snsId == null) Color.Red else Color.Black
                        )

                        Text(
                            text = "ID 어떤아이디넣지",
                            fontFamily = MainFont,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFADADAD)
                        )
                    }

                    Text(
                        text = userInfo.defaultRewardPhrase,
                        fontFamily = MainFont,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF686868)
                    )
                }
            }


            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "업적",
                    style = TextStyle(
                        fontFamily = MainFont,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // 최대 3개의 업적만 표시
                    val displayRewards = userInfo.userRewards.take(3)

                    // 3개 슬롯을 채우기 위해 빈 슬롯 추가
                    repeat(3) { index ->
                        if (index < displayRewards.size) {
                            val reward = displayRewards[index]
                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFD9D9D9))
                            ) {
                                AsyncImage(
                                    model = reward.iconImageUrl,
                                    contentDescription = reward.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        } else {
                            // 빈 슬롯
                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFD9D9D9))
                            )
                        }
                    }
                }
            }
        }
    }
}