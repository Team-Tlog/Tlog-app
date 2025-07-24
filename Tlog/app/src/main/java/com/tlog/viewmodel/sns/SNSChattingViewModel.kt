package com.tlog.viewmodel.sns

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.concurrent.TimeUnit

@SuppressLint("CheckResult")
@HiltViewModel
class SNSChattingViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {
    private lateinit var stomp: StompClient
    private lateinit var topic: Disposable

    //무조건 destination 끝에 websocket 붙이기
    private val url = "ws://43.201.77.202:8080/ws-stomp/websocket"

    private val _messageList = MutableStateFlow<List<ChatMessageDto>>(emptyList())
    val messageList: StateFlow<List<ChatMessageDto>> get() = _messageList

    init {
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

    suspend fun getMyId(): String? {
        return userPreferences.getUserId()
    }

    //서버와 연결후 구독(subscribe)를 해야하는데 구독하는 부분
    private fun handleWebSocketOpened() {
        Log.d("SNSChatting", "웹소켓 연결됨")
        topic = stomp.topic("/sub/chat/room/16").subscribe({ message ->
            Log.d("SNSChatting", "Received message: ${message.payload}")
            val json = JSONObject(message.payload)
            val chatMessage = ChatMessageDto(
                chatRoomId = json.getLong("chatRoomId"),
                senderId = json.getString("senderId"),
                senderName = json.getString("senderName"),
                content = json.getString("content"),
                sendAt = json.getString("sendAt")
            )
            _messageList.value = _messageList.value + chatMessage
        }, { error ->
            Log.e("SNSChatting", "Error receiving message", error)
        })

        viewModelScope.launch {
            val userId = userPreferences.getUserId() ?: return@launch
            //구독 후 메시지 전송하는 부분 숫자 16은 roomId
            sendMessage(userId, 16)
        }
    }

    //메시지를 보내는 부분, 무조건 서버와 형식을 맞춰야함 지금 이 상태 그대로 보내면 됨
    fun sendMessage(senderId: String, chatRoomId: Long) {
        viewModelScope.launch {
            val messageJson = JSONObject().apply {
                put("senderId", senderId)
                put("chatRoomId", chatRoomId)
                put("content", "ㅎㅇㅎㅇㅎㅇ 이게되네")
            }
            Log.d("SNSChatting", "Sending message: $messageJson")
            stomp.send("/pub/chat/message", messageJson.toString())
                .subscribe({
                    Log.d("SNSChatting", "Message sent successfully")
                }, { error ->
                    Log.e("SNSChatting", "Error sending message", error)
                })
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
                        Log.d("SNSChatting", "WebSocket Message: $text")
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

//나중에 실제로 사용할 Data Class
data class ChatMessageDto(
    val chatRoomId: Long,
    val senderId: String,
    val senderName: String,
    val content: String,
    val sendAt: String
)