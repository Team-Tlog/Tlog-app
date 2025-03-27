package com.tlog.ui.component.team

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tlog.R

@Composable
fun TeamTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filled_star),
            contentDescription = "팀 대표 이미지",
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color.White)
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "검색",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(40.dp)
        )

        Spacer(modifier = Modifier.width(5.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_notification),
            contentDescription = "알림",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(42.dp)
        )

    }
}