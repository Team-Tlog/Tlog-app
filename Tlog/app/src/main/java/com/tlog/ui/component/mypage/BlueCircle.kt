package com.tlog.ui.component.mypage

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.tlog.ui.theme.MainColor


@Composable
fun BlueCircle() {
    Box(
        modifier = Modifier
            .height(462.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(462.dp)
        ) {
            drawCircle( // 너무 어렵티비....
                color = MainColor,
                radius = 510.dp.toPx(),
                center = Offset(x = size.width / 2f, y = -120f)
            )
        }
    }
}