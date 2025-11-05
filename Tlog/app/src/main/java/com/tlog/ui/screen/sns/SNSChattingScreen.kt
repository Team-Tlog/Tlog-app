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
import androidx.compose.material3.HorizontalDivider
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
import kotlinx.coroutines.flow.filter

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
                    android.util.Log.d("SNSChatting", "🔄 Near end - last=$lastVisibleItemIndex, total=$totalItemsCount, historySize=${historyMessages.size}, loading=$isLoadingHistory, hasMore=$hasMoreHistory")
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
        // 상단 헤더
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 21.dp, top = 19.dp, bottom = 19.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = teamName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = MainFont,
                modifier = Modifier.padding(start = 10.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy((-9).dp) // 살짝 겹쳐 보이게 (6이 맞는거같은데 추후 물어보고 수정)
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

        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)

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

                ChatBubble(
                    message = message,
                    myId = myId.toString(),
                    profileImageUrl = senderProfile?.profileImageUrl
                )

                Spacer(modifier = Modifier.height(8.dp))
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