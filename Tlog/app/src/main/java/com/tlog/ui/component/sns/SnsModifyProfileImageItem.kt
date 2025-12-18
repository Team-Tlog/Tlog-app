package com.tlog.ui.component.sns

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.ui.theme.DefaultImage

@Composable
fun SnsModifyProfileImageItem(
    imageUrl: String,
    imageChangeClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(93.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "프로필 사진",
            contentScale = ContentScale.Crop,
            error = painterResource(DefaultImage),
            modifier = Modifier
                .align(Alignment.Center)
                .size(90.dp)
                .clip(RoundedCornerShape(50))
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "프로필 사진 수정",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.BottomEnd)
                .shadow(0.5.dp, shape = RoundedCornerShape(50))
                .background(Color.White)
                .clickable { imageChangeClick() }
        )
    }
}