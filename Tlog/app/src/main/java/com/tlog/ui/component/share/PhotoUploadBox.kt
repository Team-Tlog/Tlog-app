package com.tlog.ui.component.share

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.tlog.R
import com.tlog.ui.theme.FontGray
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont


@Composable
fun PhotoUploadBox(
    modifier: Modifier = Modifier,
    images: List<Uri>,
    maxImageCnt: Int = 5,
    onAddClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "사진 등록",
            fontFamily = MainFont,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )
        Text(
            text = "${images.size}/${maxImageCnt}",
            color = FontGray,
            fontFamily = MainFont,
            fontSize = 13.sp
        )
    }

    LazyRow (
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(images) { imageUri ->
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = null,
                modifier = Modifier
                    .size(83.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFDCDCDC))
            )
        }
        item {
            if (images.size < maxImageCnt) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0xFFDCDCDC), RoundedCornerShape(16.dp))
                        .clickable { onAddClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_circle),
                        contentDescription = "사진 추가",
                        tint = MainColor.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }

}