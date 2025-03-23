package com.tlog.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainFont
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.graphicsLayer
import com.tlog.ui.theme.MainColor

@Composable
fun DestinationCard(
    name: String,
    image: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(150.dp)
            .height(110.dp)
            .graphicsLayer {
                shadowElevation = 4.dp.toPx()
                shape = RoundedCornerShape(16.dp)
                clip = true // 그림자 영역 자르기
            }
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) MainColor else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = name,
            fontSize = 16.sp,
            fontFamily = MainFont,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 23.dp, bottom = 21.dp)
        )
    }
}