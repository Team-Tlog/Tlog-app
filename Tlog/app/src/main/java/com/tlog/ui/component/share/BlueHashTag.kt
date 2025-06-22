package com.tlog.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.BackgroundBlue
import com.tlog.ui.theme.Essential
import com.tlog.ui.theme.MainFont


@Composable
fun BlueHashTag(
    tag: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4))
            .background(BackgroundBlue)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 6.dp, vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = tag,
                fontSize = 10.sp,
                fontFamily = MainFont,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Essential
            )
        }
    }
}