package com.tlog.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tlog.R

@Composable
fun LoginIcon(
    @DrawableRes iconResId: Int,
    loginName : String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Image(
        painter = painterResource(iconResId), // 추후 아이콘 변경 필요
        contentDescription = "소셜로그인 $loginName",
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .clickable { onClick() }
    )
}