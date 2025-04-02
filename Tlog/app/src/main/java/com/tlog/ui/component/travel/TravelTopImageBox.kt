package com.tlog.ui.component.travel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tlog.R


@Composable
fun TravelTopImageBox(
    imageUrl: Int // 알아서 바꾸기 URL로
) {
    Box( // 상단 이미지 + topbar
        modifier = Modifier
            .fillMaxWidth()
            .height(319.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
    ) {
        Image(
            painter = painterResource(imageUrl), // 너 이미지
            contentDescription = "여행지 샤진",
            contentScale = ContentScale.Crop, // 비율 맞춰 채우기
            modifier = Modifier.matchParentSize() // Box 크기에 맞추기
        )

        TravelInfoTopBar(
            iconList = listOf(R.drawable.ic_heart),
            topBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() // 상단도 그림으로 채워지게 하기 위해서 -> 상단바 크기 자동으로 가져와줌
        )
    }
}