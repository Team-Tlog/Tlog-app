package com.tlog.ui.component.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
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
import com.tlog.R
import com.tlog.ui.theme.MainFont

@Composable
fun LoginBubble() {
    Box(
        modifier = Modifier
            .width(127.dp)
            .height(45.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_login_bubble),
            contentDescription = "login_icon",
            contentScale = ContentScale.None,
            modifier = Modifier
                .matchParentSize()
        )
        Text(
            text = "3초만에 로그인 ⚡",
            color = Color.Black,
            fontFamily = MainFont,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 6.dp)
        )
    }
}