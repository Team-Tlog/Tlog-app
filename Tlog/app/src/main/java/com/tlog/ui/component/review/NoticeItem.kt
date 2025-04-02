package com.tlog.ui.component.review

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainFont

@Composable
fun NoticeItem(
    title: String,
    body: String
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "✅",
            fontSize = 14.sp,
            modifier = Modifier.padding(end = 6.dp)
        )
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = MainFont,
            color = Color.Black
        )
    }

    Spacer(modifier = Modifier.height(4.dp))

    Text(
        text = body,
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        fontFamily = MainFont,
        color = Color.Black,
        modifier = Modifier.padding(start = 22.dp) // 체크모양 맞춰 들여쓰기
    )

    Spacer(modifier = Modifier.height(14.dp))
}
