package com.tlog.ui.component.share
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoSlidingImageRes(
    @DrawableRes imageRes: List<Int>,
    intervalMs: Long = 2500,
    animationMs: Int = 1200,
    width: Int = 190,
    height: Int = 190,
    userScrollEnabled: Boolean = true
) {
    val pagerState = rememberPagerState(
        pageCount = { imageRes.size },
        initialPage = 0
    )

    val currentInterval by rememberUpdatedState(intervalMs)

    LaunchedEffect(pagerState.pageCount) {
        while (isActive) { // isActive -> 코루틴이 살아있으면 true
            delay(currentInterval)
            val next = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(next, animationSpec = tween(animationMs))
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .width(width.dp)
            .height(height.dp),
        userScrollEnabled = userScrollEnabled
    ) { page ->
        Image(
            painter = painterResource(id = imageRes[page]),
            contentDescription = "TBTI 캐릭터",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}