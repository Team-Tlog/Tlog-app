package com.tlog.ui.component.sns

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.tlog.R
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.sns.ChatMessageDto

@Composable
fun ChatBubble(
    message: ChatMessageDto,
    myId: String,
    profileImageUrl: String? = null,
    showProfile: Boolean = true
) {
    val isMine = message.senderId == myId

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = if (showProfile) 10.dp else 0.dp,
                bottom = 4.dp
            ),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
        if (!isMine) {
            // 다른 사람 메시지 - 왼쪽 (프로필 아이콘 + 닉네임 + 회색 말풍선)
            Row(
                modifier = Modifier.padding(start = 24.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                if (showProfile) {
                    // 프로필 아이콘 (27*28)
                    AsyncImage(
                        model = profileImageUrl,
                        contentDescription = "프로필 사진",
                        modifier = Modifier
                            .size(27.dp, 28.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentScale = ContentScale.Crop,
                        error = painterResource(id = R.drawable.destination_img)
                    )

                    Spacer(modifier = Modifier.width(4.dp))
                } else {
                    // 프로필 자리 공간 유지 (27 + 4)
                    Spacer(modifier = Modifier.width(31.dp))
                }

                Column {
                    if (showProfile) {
                        // 닉네임 (12px, regular)
                        Text(
                            text = message.senderName,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = MainFont,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    // 메시지 말풍선과 unreadCount
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        // 메시지 말풍선 (상대: #E2E2E9)
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFE2E2E9),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = message.content,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = MainFont,
                                color = Color.Black
                            )
                        }

                        // unreadCount 표시 (0이 아닌 경우에만)
                        if (message.unreadCount > 0) {
                            Text(
                                text = message.unreadCount.toString(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = MainFont,
                                color = Color.Gray,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
            }
        } else {
            // 내 메시지 - 오른쪽 (파란색 말풍선)
            Row(
                modifier = Modifier.padding(end = 24.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                // unreadCount 표시 (0이 아닌 경우에만)
                if (message.unreadCount > 0) {
                    Text(
                        text = message.unreadCount.toString(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = MainFont,
                        color = Color(0xFF3C6AFF),
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }

                // 메시지 말풍선 (내: #3C6AFF)
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFF3C6AFF),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = message.content,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = MainFont,
                        color = Color.White
                    )
                }
            }
        }
    }
}