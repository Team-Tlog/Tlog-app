package com.tlog.ui.component.tbti

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainColor


@Composable
fun TbtiProgressBar(
    current: Int = 1, // 질문 번호
    total: Int = 10, // 총 질문 개수
    barWidth: androidx.compose.ui.unit.Dp? = null // 추가: barWidth 지정 가능
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally // 중앙 정렬
    ) {
        val progress = current.toFloat() / total.toFloat()

        Spacer(modifier = Modifier.height(9.dp))

        LinearProgressIndicator(
            progress = progress,
            color = MainColor,
            trackColor = Color(0xFFF0F0F0),
            modifier = Modifier
                .width(barWidth ?: 184.dp)
                .height(5.dp)
                .clip(RoundedCornerShape(50))
        )
    }
}