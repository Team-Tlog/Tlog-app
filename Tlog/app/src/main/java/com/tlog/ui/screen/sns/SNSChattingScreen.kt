package com.tlog.ui.screen.sns

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tlog.ui.component.sns.ChatBubble
import com.tlog.viewmodel.sns.SNSChattingViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SNSChattingScreen(viewModel: SNSChattingViewModel = hiltViewModel()) {
    val messages by viewModel.messageList.collectAsState()
    var myId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        myId = viewModel.getMyId()
    }

    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        listState.animateScrollToItem(0)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(bottom = 20.dp),
        state = listState,
        reverseLayout = true
    ) {
        items(items = messages) { message ->
            ChatBubble(message = message, myId = myId ?: "")
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}