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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tlog.ui.component.sns.ChatBubble
import com.tlog.viewmodel.sns.SNSChattingViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tlog.ui.theme.MainFont
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.viewmodel.sns.MemberProfile
import kotlinx.coroutines.flow.distinctUntilChanged
import com.tlog.ui.component.sns.ChatInputBox
@Composable
fun SNSChattingScreen(
    chatRoomId: Long,
    teamName: String,
    members: List<MemberProfile>,
    viewModel: SNSChattingViewModel = hiltViewModel(),
) {
    val messages by viewModel.messageList.collectAsState()
    val historyMessages by viewModel.displayedHistoryMessages.collectAsState()
    val isLoadingHistory by viewModel.isLoadingHistory.collectAsState()
    val hasMoreHistory by viewModel.hasMoreHistory.collectAsState()
    val memberProfiles by viewModel.memberProfiles.collectAsState()
    var myId by remember { mutableStateOf<String?>(null) }
    var messageText by remember { mutableStateOf("") }

    // 모든 메시지를 하나로 합치기 (히스토리 + 실시간)
    val allMessages = remember(historyMessages, messages) {
        (historyMessages + messages).sortedByDescending { it.messageId }
    }

    LaunchedEffect(chatRoomId) {
        myId = viewModel.getMyId()
        viewModel.setMemberProfiles(members)
        viewModel.initChatRoom(chatRoomId)
    }

    val listState = rememberLazyListState()

    // 새로운 실시간 메시지가 도착하면 스크롤
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty() && listState.firstVisibleItemIndex < 3) {
            listState.animateScrollToItem(0)
        }
    }

    // 스크롤 위치 감지 - 리스트 끝에 도달하면 더 로드
    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val totalItemsCount = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            Pair(lastVisibleItemIndex, totalItemsCount)
        }
            .distinctUntilChanged()
            .collect { (lastVisibleItemIndex, totalItemsCount) ->
                // 리스트 끝에서 3개 이내에 도달하면 더 로드
                if (totalItemsCount > 0 &&
                    lastVisibleItemIndex >= totalItemsCount - 3 &&
                    !isLoadingHistory &&
                    hasMoreHistory) {
                    viewModel.loadMoreHistory()
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
            .imePadding()
    ) {
        Spacer(modifier = Modifier.height(6.dp))

        // 상단 헤더 (360*60, box-shadow)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .background(Color.White)
                .padding(start = 24.dp, end = 21.dp, top = 19.dp, bottom = 19.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = teamName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = MainFont,
                maxLines = 1
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy((-6).dp) // 살짝 겹쳐 보이게
            ) {
                val profileImages = members.map { it.profileImageUrl ?: "" }

                profileImages.take(5).forEach { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = "팀원 이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        error = painterResource(id = R.drawable.destination_img)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Today 라벨
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(47.dp)
                    .height(30.dp)
                    .background(
                        color = Color(0xFFF1F4FD),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Today",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    fontFamily = MainFont,
                    maxLines = 1
                )
            }
        }

        // 채팅 리스트
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(horizontal = 12.dp),
            state = listState,
            reverseLayout = true, // 최신 메시지를 아래로
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            // 모든 메시지 표시 (이미 최신순으로 정렬됨)
            items(
                count = allMessages.size,
                key = { index -> allMessages[index].messageId }
            ) { index ->
                val message = allMessages[index]

                // 메시지가 표시될 때 읽음 처리
                LaunchedEffect(message.messageId) {
                    viewModel.markMessageAsRead(message.messageId)
                }

                val senderProfile = memberProfiles[message.senderId]

                // 이전 메시지와 같은 사용자인지 확인
                val previousMessage = if (index < allMessages.size - 1) allMessages[index + 1] else null
                val isConsecutive = previousMessage?.senderId == message.senderId

                ChatBubble(
                    message = message,
                    myId = myId.toString(),
                    profileImageUrl = senderProfile?.profileImageUrl,
                    showProfile = !isConsecutive
                )
            }

            // 로딩 인디케이터 (가장 위, reverseLayout이므로 아래에 위치)
            if (isLoadingHistory && hasMoreHistory) {
                item(key = "loading") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "메시지 로딩 중...",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontFamily = MainFont
                        )
                    }
                }
            }
        }

        // 입력창과 전송 버튼
        ChatInputBox(
            messageText = messageText,
            onMessageChange = { messageText = it },
            onSendClick = {
                val id = myId
                if (!messageText.isBlank() && id != null) {
                    viewModel.sendMessage(id, chatRoomId = chatRoomId, content = messageText)
                    messageText = ""
                }
            }
        )
    }
}