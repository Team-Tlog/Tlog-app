package com.tlog.ui.component.sns

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tlog.ui.style.Body1Bold
import com.tlog.viewmodel.sns.ChatMessageDto

@Composable
fun ChatBubble(message: ChatMessageDto, myId: String) {
    val isMine = message.senderId == myId
    val time = try {
        val timePart = message.sendAt.split("T").getOrNull(1)?.substring(0, 5)
        timePart ?: "--:--"
    } catch (e: Exception) {
        "--:--"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = if (isMine) 48.dp else 0.dp,
                end = if (isMine) 0.dp else 48.dp
            ),
        horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
    ) {
        Text(
            text = message.senderName,
            style = Body1Bold,
            color = Color.Gray
        )
        Box(
            modifier = Modifier
                .background(
                    color = if (isMine) Color(0xFFDCF8C6) else Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.content,
                style = Body1Bold
            )
        }
        Text(
            text = time,
            style = Body1Bold,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}