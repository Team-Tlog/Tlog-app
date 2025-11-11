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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// 날짜 포맷팅 함수: "오늘"이면 "Today", 다른 날이면 "yyyy.MM.dd" 형식으로 반환
private fun formatDateLabel(sendAt: String): String {
    return try {
        val dateTime = LocalDateTime.parse(sendAt, DateTimeFormatter.ISO_DATE_TIME)
        val messageDate = dateTime.toLocalDate()
        val today = LocalDate.now()

        if (messageDate == today) {
            "Today"
        } else {
            dateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        }
    } catch (e: Exception) {
        "Today" // 파싱 실패 시 기본값
    }
}

// 메시지의 날짜만 추출 (yyyy-MM-dd)
private fun extractDate(sendAt: String): String {
    return try {
        val dateTime = LocalDateTime.parse(sendAt, DateTimeFormatter.ISO_DATE_TIME)
        dateTime.toLocalDate().toString()
    } catch (e: Exception) {
        LocalDate.now().toString() // 파싱 실패 시 오늘 날짜
    }
}

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

    // 모든 메시지를 하나로 합치기 (히스토리 + 실시간), 중복 제거
    val allMessages = remember(historyMessages, messages) {
        val combined = (historyMessages + messages)
            .distinctBy { it.messageId } // messageId로 중복 제거
            .sortedByDescending { it.messageId }

        android.util.Log.d("SNSChatting", "📦 Total messages: history=${historyMessages.size}, realtime=${messages.size}, combined=${combined.size}")
        combined
    }

    // 날짜별로 그룹화된 메시지 (날짜별로 구분하기 위해)
    data class MessageItem(val date: String?, val message: com.tlog.viewmodel.sns.ChatMessageDto?)

    val messageItems = remember(allMessages) {
        val items = mutableListOf<MessageItem>()
        var lastDate: String? = null

        android.util.Log.d("SNSChatting", "🎨 Creating messageItems from ${allMessages.size} messages")

        // reverseLayout이므로 역순으로 처리해서 날짜 라벨이 위에 오도록
        allMessages.forEachIndexed { index, message ->
            val currentDate = extractDate(message.sendAt)
            val nextMessage = allMessages.getOrNull(index + 1)
            val nextDate = nextMessage?.let { extractDate(it.sendAt) }

            // 메시지 먼저 추가
            items.add(MessageItem(date = null, message = message))

            // 다음 메시지의 날짜가 다르거나 마지막 메시지인 경우 날짜 라벨 추가
            if (nextDate != currentDate) {
                items.add(MessageItem(date = currentDate, message = null))
            }
        }

        android.util.Log.d("SNSChatting", "🎨 Created ${items.size} messageItems (messages + date labels)")
        items
    }

    LaunchedEffect(chatRoomId) {
        myId = viewModel.getMyId()
        android.util.Log.d("SNSChatting", "🆔 My ID loaded: $myId")
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
    LaunchedEffect(listState, messageItems.size) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val totalItemsCount = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            Triple(lastVisibleItemIndex, totalItemsCount, layoutInfo.visibleItemsInfo.size)
        }
            .distinctUntilChanged()
            .collect { (lastVisibleItemIndex, totalItemsCount, visibleItemsCount) ->
                android.util.Log.d("SNSChatting", "📍 Scroll detected - last=$lastVisibleItemIndex, total=$totalItemsCount, visible=$visibleItemsCount, isLoading=$isLoadingHistory, hasMore=$hasMoreHistory")

                // reverseLayout이므로 리스트 끝(위로 스크롤)은 큰 인덱스
                // 리스트 끝에서 5개 이내에 도달하면 더 로드
                val shouldLoadMore = totalItemsCount > 0 &&
                    lastVisibleItemIndex >= totalItemsCount - 5 &&
                    !isLoadingHistory &&
                    hasMoreHistory

                if (shouldLoadMore) {
                    android.util.Log.d("SNSChatting", "🔥 Infinite scroll triggered! Loading more history...")
                    viewModel.loadMoreHistory()
                } else if (totalItemsCount > 0) {
                    android.util.Log.d("SNSChatting", "⏸️ Scroll condition not met: needMore=${lastVisibleItemIndex >= totalItemsCount - 5}, notLoading=${!isLoadingHistory}, hasMore=$hasMoreHistory")
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
            // 날짜 라벨과 메시지를 함께 표시
            items(
                count = messageItems.size,
                key = { index ->
                    val item = messageItems[index]
                    if (item.date != null) "date_${item.date}" else "msg_${item.message?.messageId}"
                }
            ) { index ->
                val item = messageItems[index]

                // 날짜 라벨 표시
                if (item.date != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFF1F4FD),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // 저장된 날짜를 사용해서 라벨 포맷팅
                            val dateLabel = try {
                                val date = LocalDate.parse(item.date)
                                val today = LocalDate.now()

                                if (date == today) {
                                    "Today"
                                } else {
                                    date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                                }
                            } catch (_: Exception) {
                                "Today"
                            }

                            Text(
                                text = dateLabel,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black,
                                fontFamily = MainFont,
                                maxLines = 1
                            )
                        }
                    }
                }
                // 메시지 표시
                else if (item.message != null) {
                    val message = item.message

                    // 메시지가 표시될 때 읽음 처리
                    LaunchedEffect(message.messageId) {
                        viewModel.markMessageAsRead(message.messageId)
                    }

                    val senderProfile = memberProfiles[message.senderId]

                    // 이전 메시지와 같은 사용자인지 확인 (날짜 라벨 제외)
                    val previousItem = if (index < messageItems.size - 1) messageItems[index + 1] else null
                    val previousMessage = previousItem?.message
                    val isConsecutive = previousMessage?.senderId == message.senderId

                    // myId가 null이 아닐 때만 ChatBubble 표시
                    myId?.let {
                        ChatBubble(
                            message = message,
                            myId = it,
                            profileImageUrl = senderProfile?.profileImageUrl,
                            showProfile = !isConsecutive
                        )
                    }
                }
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