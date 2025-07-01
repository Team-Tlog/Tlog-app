package com.tlog.ui.component.travel.review

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import com.tlog.ui.theme.MainColor


@Composable
fun ReviewProgressBar(
    cnt: Int,
    totalCnt: Int,
    modifier: Modifier = Modifier
) {
    val progress = (cnt.toFloat()/totalCnt.toFloat())

    LinearProgressIndicator(
        progress = { progress },
        color = MainColor,
        trackColor = Color(0xFFE5E8F0),
        strokeCap = StrokeCap.Round,
        modifier = modifier
    )
}