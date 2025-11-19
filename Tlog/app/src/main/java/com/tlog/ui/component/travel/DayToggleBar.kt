package com.tlog.ui.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainColor
@Composable
fun DayToggleBar(
    modifier: Modifier = Modifier,
    size: Int,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color(0xFFF1F4FD)),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 10.dp)
        ) {
            items(size) {
                val day = it + 1
                val isSelected = selectedDay == day

                Box(
                    modifier = Modifier
                        .padding(vertical = (5.5).dp)
                        .clip(CircleShape)
                        .background(if (isSelected) MainColor else Color.Transparent)
                        .clickable { onDaySelected(day) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Day$day",
                        fontSize = 14.sp,
                        color = if (isSelected) Color.White else Color(0xFF8DAEEC),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier
                            .padding(horizontal = 13.dp, vertical = 6.dp)

                    )
                }
            }
        }
    }
}

