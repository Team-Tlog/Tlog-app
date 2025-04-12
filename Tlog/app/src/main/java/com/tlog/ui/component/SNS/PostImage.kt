package com.tlog.ui.component.SNS


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostImage(
    images: List<String>,
    iconResId: Int,
    modifier: Modifier = Modifier,
    height: Int = 430,
    iconTint: Color = Color.White,
    activeIndicatorColor: Color = Color.White,
    inactiveIndicatorColor: Color = Color.Gray,
    onPageChanged: (Int) -> Unit = {} //  현재 페이지 콜백 추가
) {
    if (images.isNotEmpty()) {
        val pagerState = rememberPagerState(pageCount = { images.size })

        // 페이지 변경될 때마다 콜백 실행
        LaunchedEffect(pagerState.currentPage) {
            onPageChanged(pagerState.currentPage)
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(height.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = images[page],
                        contentDescription = "게시물 이미지 ${page + 1}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // 이미지가 여러 개인 경우 표시할 아이콘
//            if (images.size > 1) {            //추후 추가되면 사용 하면 될 듯, 필요 없으면 삭제
//                Icon(
//                    painter = painterResource(id = iconResId),
//                    contentDescription = "여러 이미지",
//                    tint = iconTint,
//                    modifier = Modifier
//                        .align(Alignment.BottomEnd)
//                        .padding(end = 12.dp, bottom = 8.dp)
//                        .size(42.dp)
//                )
//            }

            // 현재 페이지 표시기
            if (images.size > 1) {                              //테스트 할때 보기 싶게 만들어놈, 사진이 여러장일때 어떻게 표시 할지 물어보고 추후 변경 or 삭제
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(images.size) { index ->
                        val color = if (pagerState.currentPage == index) activeIndicatorColor else inactiveIndicatorColor
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(8.dp)
                                .background(color, CircleShape)
                        )
                    }
                }
            }
        }
    } else {
        // 이미지가 없는 경우 기본 표시
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(height.dp)
                .background(Color.Gray)
        )
    }
}