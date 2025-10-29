package com.tlog.ui.screen.sns

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tlog.ui.component.sns.ChatBubble
import com.tlog.viewmodel.sns.SNSChattingViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tlog.ui.theme.MainFont

@Composable
fun SNSChattingScreen(
    chatRoomId: Long,
    viewModel: SNSChattingViewModel = hiltViewModel()
) {
    val messages by viewModel.messageList.collectAsState()
    var myId by remember { mutableStateOf<String?>(null) }
    var messageText by remember { mutableStateOf("") }

    LaunchedEffect(chatRoomId) {
        myId = viewModel.getMyId()
        viewModel.initChatRoom(chatRoomId)
    }

    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        listState.animateScrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
            .imePadding()
    ) {
        // 상단 헤더
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "틀별명",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = MainFont
            )
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                // 프로필 아이콘들 (임시로 원형 박스)
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                }
            }
        }

        Divider(color = Color.LightGray, thickness = 1.dp)

        // Today 라벨
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Today",
                fontSize = 14.sp,
                color = Color.Gray,
                fontFamily = MainFont
            )
        }
        // 채팅 리스트
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(horizontal = 12.dp),
            reverseLayout = true, // 최신 메시지를 아래로
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(messages.reversed()) { message ->
                ChatBubble(message, myId.toString())
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // 입력창과 전송 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        "",
                        color = Color.LightGray
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray,
                ),
                singleLine = true
            )
            IconButton(
                onClick = {
                    val id = myId
                    if (!messageText.isBlank() && id != null) {
                        viewModel.sendMessage(id, chatRoomId = chatRoomId, content = messageText)
                        messageText = ""
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "전송",
                    tint = Color(0xFF5B8CFF),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}