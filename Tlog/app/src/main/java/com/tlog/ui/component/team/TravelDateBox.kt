package com.tlog.ui.component.team

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tlog.ui.style.Body1Regular

@Composable
fun TravelDateBox(
    startDate: String,
    endDate: String
) {
    Box(
        modifier = Modifier
        .height(34.dp)
        .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp, bottomStart = 50.dp, bottomEnd = 50.dp))
        .background(Color(0x42000000)),
        contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${startDate} ~ ${endDate}",
                color = Color.White,
                textAlign = TextAlign.Center,
                style = Body1Regular,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
    }
}