package com.tlog.ui.screen.sns

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.ui.component.share.TopBar
import com.tlog.ui.component.sns.RecentTravelCourseGroup
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.sns.SnsPostViewModel


@Preview
@OptIn(ExperimentalFoundationApi::class)
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

        Spacer(modifier = Modifier.height(26.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(455.dp)
        ) {

            if (viewModel.selectImages.value.isNotEmpty()) {
                val pagerState =  rememberPagerState(pageCount = { viewModel.selectImages.value.size })
                val size = viewModel.selectImages.value.size

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) { page ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        AsyncImage(
                            model = viewModel.selectImages.value[page],
                            contentDescription = "선택한 사진",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )

                        Icon(
                            painter = painterResource(R.drawable.ic_image_stack),
                            contentDescription = "",
                            tint = if (size > 1) MainColor else Color.Unspecified,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 12.dp, bottom = 8.dp)
                                .size(42.dp)
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFD9D9D9))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_image_stack),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 12.dp, bottom = 8.dp)
                            .size(42.dp)
                    )
                }
            }
        }



    }
}