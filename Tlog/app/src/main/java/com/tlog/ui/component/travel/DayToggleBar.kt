package com.tlog.ui.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
fun DayToggleBar(
    selectedDay: Int,
    onDaySelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .width(260.dp)
            .height(45.dp)
            .clip(CircleShape)
            .background(Color(0xFFF1F4FD)), // 배경 원
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            (1..3).forEach { day ->
                val isSelected = selectedDay == day
                Box(
                    modifier = Modifier
                        .width(62.dp)
                        .height(35.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) MainColor else Color.Transparent)
                        .clickable { onDaySelected(day) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Day$day",
                        fontSize = 14.sp,
                        color = if (isSelected) Color.White else MainColor
                    )
                }
            }
        }
    }
}
