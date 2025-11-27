package com.tlog.viewmodel.sns

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import ua.naiksoftware.stomp.client.StompClient
import ua.naiksoftware.stomp.LifecycleEvent
import ua.naiksoftware.stomp.ConnectionProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.local.UserPreferences
import com.tlog.data.model.response.sns.ChatMessageHistory
import com.tlog.data.model.team.ChatMessageDto
import com.tlog.data.model.team.MemberProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.concurrent.TimeUnit

@SuppressLint("CheckResult")
@HiltViewModel
class SNSChattingViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val snsApi: com.tlog.api.SnsApi
) : ViewModel() {
    private lateinit var stomp: StompClient
    private lateinit var topic: Disposable

    //무조건 destination 끝에 websocket 붙이기
    private val url = "ws://43.201.77.202:8080/ws-stomp/websocket"

    private val _messageList = MutableStateFlow<List<ChatMessageDto>>(emptyList())
    val messageList = _messageList.asStateFlow()

    var messageText by mutableStateOf("")
    var messages = mutableStateListOf<ChatMessageDto>()

    private var currentChatRoomId: Long = 0

    // 메시지 히스토리 페이지네이션 관련 (단순화)
    private val _displayedHistoryMessages = MutableStateFlow<List<ChatMessageDto>>(emptyList())
    val displayedHistoryMessages = _displayedHistoryMessages.asStateFlow()

    private val _isLoadingHistory = MutableStateFlow(false)
    val isLoadingHistory = _isLoadingHistory.asStateFlow()

    private val _hasMoreHistory = MutableStateFlow(true)
    val hasMoreHistory = _hasMoreHistory.asStateFlow()

    // 다음 커서 저장 (페이지네이션용)
    private var nextCursor: Long? = null

    // 멤버 프로필 정보 저장 (userId -> profileImageUrl)
    private val _memberProfiles = MutableStateFlow<Map<String, MemberProfile>>(emptyMap())
    val memberProfiles = _memberProfiles.asStateFlow()

    fun setMemberProfiles(members: List<MemberProfile>) {
        _memberProfiles.value = members.associateBy { it.userId }
    }

    fun initChatRoom(chatRoomId: Long) {
        currentChatRoomId = chatRoomId
        Log.d("SNSChatting", "Initializing chat room: $chatRoomId")

        // 초기 히스토리 로드
        loadInitialHistory()

        val connectionProvider = MyConnectionProvider(url, userPreferences)
        stomp = StompClient(connectionProvider)
        connectionProvider.connect()
        stomp.connect()

        stomp.lifecycle().subscribe { event ->
            when (event.type) {
                //OPENED이라는 응답이 오면 구독 전송
                LifecycleEvent.Type.OPENED -> handleWebSocketOpened()
                LifecycleEvent.Type.CLOSED -> Log.d("SNSChatting", "WebSocket Closed")
                LifecycleEvent.Type.ERROR -> Log.e("SNSChatting", "WebSocket Error")
                else -> {}
            }
        }
    }

    // 초기 히스토리 로드 (50개 전부 표시)
    private fun loadInitialHistory() {
        viewModelScope.launch {
            try {
                _isLoadingHistory.value = true
                val response = snsApi.getChatMessageHistory(
                    roomId = currentChatRoomId,
                    size = 50
                )

                if (response.status == 200) {
                    // 서버에서 받은 메시지를 ChatMessageDto로 변환
                    val messages = response.data.messages.map { history: ChatMessageHistory ->
                        ChatMessageDto(
                            messageId = history.id,
                            chatRoomId = history.chatRoomId,
                            senderId = history.senderId,
                            senderName = history.senderName,
                            content = history.content,
                            sendAt = history.sendAt,
                            unreadCount = history.unreadCount
                        )
                    }

                    // 서버에서 받은 메시지를 바로 전부 표시
                    _displayedHistoryMessages.value = messages

                    // nextCursor 저장
                    nextCursor = response.data.nextCursor

                    // nextCursor가 null이면 더 이상 히스토리가 없음 (hasNext 대신 nextCursor로 판단)
                    _hasMoreHistory.value = response.data.nextCursor != null

                }
            } catch (e: Exception) {
                Log.e("SNSChatting", "Error loading message history", e)
            } finally {
                _isLoadingHistory.value = false
            }
        }
    }

    // 서버에서 다음 50개 메시지 로드
    fun loadMoreHistory() {
        viewModelScope.launch {
            if (_isLoadingHistory.value || !_hasMoreHistory.value) {
                return@launch
            }

            try {
                _isLoadingHistory.value = true

                // nextCursor를 beforeMessageId로 사용
                val cursorToUse = nextCursor

                val response = snsApi.getChatMessageHistory(
                    roomId = currentChatRoomId,
                    size = 50,
                    beforeMessageId = cursorToUse
                )

                if (response.status == 200) {
                    val newMessages = response.data.messages.map { history: ChatMessageHistory ->
                        ChatMessageDto(
                            messageId = history.id,
                            chatRoomId = history.chatRoomId,
                            senderId = history.senderId,
                            senderName = history.senderName,
                            content = history.content,
                            sendAt = history.sendAt,
                            unreadCount = history.unreadCount
                        )
                    }

                    // 기존 메시지에 새로운 메시지 추가
                    _displayedHistoryMessages.value = _displayedHistoryMessages.value + newMessages

                    // nextCursor 업데이트
                    nextCursor = response.data.nextCursor

                    // nextCursor가 null이면 더 이상 히스토리가 없음 (hasNext 대신 nextCursor로 판단)
                    _hasMoreHistory.value = response.data.nextCursor != null

                }
            } catch (e: Exception) {
                Log.e("SNSChatting", "Error loading more message history", e)
            } finally {
                _isLoadingHistory.value = false
            }
        }
    }

    suspend fun getMyId(): String? {
        return userPreferences.getUserId()
    }

    //서버와 연결후 구독(subscribe)를 해야하는데 구독하는 부분
    private fun handleWebSocketOpened() {
        Log.d("SNSChatting", "웹소켓 연결됨 - 채팅방 ID: $currentChatRoomId")

        // 채팅 메시지 구독
        topic = stomp.topic("/sub/chat/room/$currentChatRoomId").subscribe({ message ->
            Log.d("SNSChatting", "Received WebSocket message: ${message.payload}")

            try {
                val json = JSONObject(message.payload)

                // 메시지 타입 구분: 읽음 처리 업데이트 vs 새 메시지
                val isReadUpdate = json.has("messageId") && json.has("newUnreadCount")

                if (isReadUpdate) {
                    // 읽음 처리 업데이트: {messageId, newUnreadCount}
                    val messageIdValue = json.getLong("messageId")
                    val newUnreadCount = json.getInt("newUnreadCount")

                    Log.d("SNSChatting", "Read update received - messageId: $messageIdValue, newUnreadCount: $newUnreadCount")

                    // 히스토리 메시지 업데이트
                    _displayedHistoryMessages.value = _displayedHistoryMessages.value.map { msg ->
                        if (msg.messageId == messageIdValue) {
                            msg.copy(unreadCount = newUnreadCount)
                        } else {
                            msg
                        }
                    }

                    // 실시간 메시지 업데이트
                    _messageList.value = _messageList.value.map { msg ->
                        if (msg.messageId == messageIdValue) {
                            Log.d("SNSChatting", "Realtime message updated: ${msg.messageId}, unreadCount: ${msg.unreadCount} -> $newUnreadCount")
                            msg.copy(unreadCount = newUnreadCount)
                        } else {
                            msg
                        }
                    }
                } else {
                    // 새 메시지 또는 메시지 업데이트: {id, senderId, senderName, chatRoomId, content, sendAt, unreadCount}
                    val messageIdValue = json.optLong("id", 0L)
                    val unreadCountValue = json.optInt("unreadCount", 0)

                    // 기존 메시지 찾기 (히스토리 + 실시간)
                    val existingInHistory = _displayedHistoryMessages.value.find { it.messageId == messageIdValue }
                    val existingInRealtime = _messageList.value.find { it.messageId == messageIdValue }

                    if (existingInHistory != null || existingInRealtime != null) {

                        // 히스토리 메시지 업데이트
                        _displayedHistoryMessages.value = _displayedHistoryMessages.value.map { msg ->
                            if (msg.messageId == messageIdValue) {
                                msg.copy(unreadCount = unreadCountValue)
                            } else {
                                msg
                            }
                        }

                        // 실시간 메시지 업데이트
                        _messageList.value = _messageList.value.map { msg ->
                            if (msg.messageId == messageIdValue) {
                                msg.copy(unreadCount = unreadCountValue)
                            } else {
                                msg
                            }
                        }
                    } else {

                        val chatMessage = ChatMessageDto(
                            messageId = messageIdValue,
                            chatRoomId = json.getLong("chatRoomId"),
                            senderId = json.getString("senderId"),
                            senderName = json.getString("senderName"),
                            content = json.getString("content"),
                            sendAt = json.getString("sendAt"),
                            unreadCount = unreadCountValue
                        )

                        _messageList.value = _messageList.value + chatMessage

                        // 새 메시지 읽음 처리 호출
                        markMessageAsRead(chatMessage.messageId)
                    }
                }
            } catch (e: Exception) {
                Log.e("SNSChatting", "Error parsing WebSocket message: ${message.payload}", e)
            }
        }, { error ->
            Log.e("SNSChatting", "Error receiving message", error)
        })
    }

    //메시지를 보내는 부분, 무조건 서버와 형식을 맞춰야함 지금 이 상태 그대로 보내면 됨
    fun sendMessage(senderId: String, chatRoomId: Long, content: String) {
        viewModelScope.launch {
            val messageJson = JSONObject().apply {
                put("senderId", senderId)
                put("chatRoomId", chatRoomId)
                put("content", content)
            }
            Log.d("SNSChatting", "Sending message: $messageJson")
            stomp.send("/pub/chat/message", messageJson.toString())
                .subscribe({
                    Log.d("SNSChatting", "Message sent successfully")
                    // 보낸 메시지는 서버에서 WebSocket을 통해 다시 수신됨
                }, { error ->
                    Log.e("SNSChatting", "Error sending message", error)
                })
        }
    }

    // 메시지 읽음 처리 (WebSocket만 사용)
    fun markMessageAsRead(messageId: Long) {
        viewModelScope.launch {
            try {
                val readerId = userPreferences.getUserId()
                if (readerId != null) {
                    // WebSocket으로 읽음 처리 메시지 전송
                    // 서버가 받아서 처리하고 /sub/chat/room/{chatRoomId}로 브로드캐스트할 것임
                    sendReadMessage(readerId, messageId)
                }
            } catch (e: Exception) {
                Log.e("SNSChatting", "Error marking message as read", e)
            }
        }
    }

    // WebSocket으로 읽음 처리 메시지 전송
    private fun sendReadMessage(readerId: String, messageId: Long) {
        viewModelScope.launch {
            try {
                val readMessageJson = JSONObject().apply {
                    put("readerId", readerId)
                    put("messageId", messageId)
                }
                Log.d("SNSChatting", "Sending read message: $readMessageJson")
                stomp.send("/pub/chat/read", readMessageJson.toString())
                    .subscribe({
                        Log.d("SNSChatting", "Read message sent successfully for messageId: $messageId")
                    }, { error ->
                        Log.e("SNSChatting", "Error sending read message", error)
                    })
            } catch (e: Exception) {
                Log.e("SNSChatting", "Error in sendReadMessage", e)
            }
        }
    }

    // 최신 unreadCount를 서버에서 가져와서 업데이트
    private fun refreshUnreadCounts() {
        viewModelScope.launch {
            try {
                val response = snsApi.getChatMessageHistory(
                    roomId = currentChatRoomId,
                    size = 50  // 최근 50개 메시지의 unreadCount 조회
                )

                if (response.status == 200) {
                    val latestUnreadCounts = response.data.messages.associate {
                        it.id to it.unreadCount
                    }

                    // 히스토리 메시지 업데이트
                    _displayedHistoryMessages.value = _displayedHistoryMessages.value.map { msg ->
                        val newUnreadCount = latestUnreadCounts[msg.messageId]
                        if (newUnreadCount != null) {
                            msg.copy(unreadCount = newUnreadCount)
                        } else {
                            msg
                        }
                    }

                    // 실시간 메시지 업데이트
                    _messageList.value = _messageList.value.map { msg ->
                        val newUnreadCount = latestUnreadCounts[msg.messageId]
                        if (newUnreadCount != null) {
                            msg.copy(unreadCount = newUnreadCount)
                        } else {
                            msg
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("SNSChatting", "Error refreshing unreadCounts", e)
            }
        }
    }

    //처음 연결을 도와주는 부분
    class MyConnectionProvider(
        private val url: String,
        private val userPreferences: UserPreferences
    ) : ConnectionProvider {
        private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        private var webSocket: WebSocket? = null
        private val messageSubject = PublishSubject.create<String>()
        private val lifecycleSubject = BehaviorSubject.create<LifecycleEvent>()

        override fun messages(): Observable<String> = messageSubject
        override fun lifecycle(): Observable<LifecycleEvent> = lifecycleSubject

        override fun send(stompMessage: String): Completable {
            return Completable.create { emitter ->
                Log.d("SNSChatting", "Sending raw message: $stompMessage")
                webSocket?.send(stompMessage)
                emitter.onComplete()
            }
        }

        override fun disconnect(): Completable {
            return Completable.create { emitter ->
                webSocket?.close(1000, "Goodbye!")
                emitter.onComplete()
            }
        }

        override fun setServerHeartbeat(ms: Int) {}
        override fun setClientHeartbeat(ms: Int) {}

        fun connect() {
            kotlinx.coroutines.GlobalScope.launch(Dispatchers.IO) {
                val token = userPreferences.getAccessToken()
                Log.d("SNSChatting", "Connecting to WebSocket URL: $url with token: $token")
                val request = Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer $token")
                    .build()

                webSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
                    override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                        Log.d("SNSChatting", "WebSocket Opened: $response")
                        lifecycleSubject.onNext(LifecycleEvent(LifecycleEvent.Type.OPENED))
                    }

                    override fun onMessage(webSocket: WebSocket, text: String) {
                        Log.d("SNSChatting", "WebSocket Raw Message: $text")
                        messageSubject.onNext(text)
                    }

                    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                        Log.d("SNSChatting", "WebSocket Closed: code=$code, reason=$reason")
                        lifecycleSubject.onNext(LifecycleEvent(LifecycleEvent.Type.CLOSED))
                    }

                    override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
                        Log.e("SNSChatting", "WebSocket Failure: ${t.message}", t)
                        lifecycleSubject.onNext(LifecycleEvent(LifecycleEvent.Type.ERROR))
                    }
                })
            }
        }
    }
}
