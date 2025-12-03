package com.tlog.ui.component.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.data.dto.share.DestinationDto
import com.tlog.ui.theme.DefaultImage
import com.tlog.ui.theme.MainFont

@Composable
fun DestinationItem(
    travel: DestinationDto,
    onDestinationClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(34.dp)
            .fillMaxWidth()
            .clickable { onDestinationClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = travel.imageUrl,
            contentDescription = "travel",
            contentScale = ContentScale.Crop,
            error = painterResource(DefaultImage),
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(9.dp))

        Text(
            text = travel.name,
            style = TextStyle(
                fontFamily = MainFont,
                fontSize = 10.sp,
                fontWeight = FontWeight.Light,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = travel.name,
            tint = Color.Black,
            modifier = Modifier
                .size(24.dp)
        )
    }
}
