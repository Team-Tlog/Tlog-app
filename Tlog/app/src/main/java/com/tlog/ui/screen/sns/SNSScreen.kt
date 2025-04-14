package com.tlog.ui.screen.sns

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.R
import com.tlog.ui.component.SNS.PostItem
import com.tlog.viewmodel.sns.SNSViewModel


@Preview
@Composable
fun SNSScreen(viewModel: SNSViewModel = viewModel()) {
    val currentUser by viewModel.currentUser.collectAsState()
    val posts by viewModel.posts.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(                                                //상단바 찬이가 만든거 넣어야함
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.LightGray)
                )  //여기에 로고 생기면 추가해야함

                Row {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(8.dp)
                            .clickable {
                                Log.d("검색 아이콘","검색 아이콘")
                            }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "검색",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(8.dp)
                            .clickable {
                                Log.d("알림 아이콘","알림 아이콘")
                            }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = "알림",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }

        // 게시물 표시
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            items(posts) { post ->
                PostItem(post = post, viewModel = viewModel)

            }
        }
    }
}