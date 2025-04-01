package com.tlog.data.model.travel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tlog.R


@Composable
fun TravelInfoTopBar(
    height: Dp = 38.dp,
    iconList: List<Int> = emptyList(),
    topBarPadding: Dp = 0.dp // 상단도 그림으로 채워지게 하기 위해서
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height + topBarPadding)
            .padding(horizontal = 14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = topBarPadding)
        ) {
            Icon(
                painter = painterResource(R.drawable.test_logo),
                contentDescription = "메인 로고",
                modifier = Modifier
                    .size(38.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            iconList.forEach { icon ->
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "아이콘",
                    tint = Color.Unspecified
                )
            }
        }

    }
}