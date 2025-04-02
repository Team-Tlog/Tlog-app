package com.tlog.ui.component.travel.review

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap


@Composable
fun reviewProgressBar(
    cnt: Int,
    totalCnt: Int,
    modifier: Modifier = Modifier
) {
    val progress = (cnt.toFloat()/totalCnt.toFloat())
    LinearProgressIndicator(
        progress = { progress },
        color = Color(0xFFD9D9D9),
        trackColor = Color.White,
        strokeCap = StrokeCap.Round,
        modifier = modifier
    )
}