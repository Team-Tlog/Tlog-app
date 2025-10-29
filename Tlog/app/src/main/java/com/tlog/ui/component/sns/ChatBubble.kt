package com.tlog.ui.component.sns

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.sns.ChatMessageDto

@Composable
fun ChatBubble(message: ChatMessageDto, myId: String) {
    val isMine = message.senderId == myId

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
        if (!isMine) {
            // 다른 사람 메시지 - 왼쪽 (프로필 아이콘 + 닉네임 + 회색 말풍선)
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                // 프로필 아이콘
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    // 닉네임
                    Text(
                        text = "닉네임",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = MainFont,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    // 메시지 말풍선
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFE8E8E8),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = message.content,
                            fontSize = 14.sp,
                            fontFamily = MainFont,
                            color = Color.Black
                        )
                    }
                }
            }
        } else {
            // 내 메시지 - 오른쪽 (파란색 말풍선)
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFF5B8CFF),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text(
                    text = message.content,
                    fontSize = 14.sp,
                    fontFamily = MainFont,
                    color = Color.White
                )
            }
        }
    }
}