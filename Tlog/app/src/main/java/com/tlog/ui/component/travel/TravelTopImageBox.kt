package com.tlog.ui.component.travel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tlog.R


@Composable
fun TravelTopImageBox(
    imageUrl: String // 알아서 바꾸기 URL로
) {
    Box( // 상단 이미지 + topbar
        modifier = Modifier
            .fillMaxWidth()
            .height(319.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "여행지 사진",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        TravelInfoTopBar(
            iconList = listOf(R.drawable.ic_heart),
            topBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() // 상단도 그림으로 채워지게 하기 위해서 -> 상단바 크기 자동으로 가져와줌
        )
    }
}