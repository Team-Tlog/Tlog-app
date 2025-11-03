package com.tlog.ui.component.share

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainFont
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tlog.ui.theme.MainColor

@Composable
fun DestinationCard(
    name: String,
    image: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .aspectRatio(150f / 110f)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) MainColor else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(image)
                .crossfade(true)  // 부드러운 전환 효과
                .build(),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
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