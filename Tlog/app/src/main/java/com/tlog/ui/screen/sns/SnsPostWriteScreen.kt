package com.tlog.ui.screen.sns

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.share.TopBar
import com.tlog.ui.component.sns.RecentTravelCourseGroup
import com.tlog.viewmodel.sns.SnsPostViewModel


@Preview
@Composable
fun SnsPostWriteScreen(viewModel: SnsPostViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        TopBar(text = "게시글 작성")

        Spacer(modifier = Modifier.height(16.dp))

        RecentTravelCourseGroup( // 최근 다녀온 코스
            modifier = Modifier
            .padding(horizontal = 24.dp)
        )
    }
}