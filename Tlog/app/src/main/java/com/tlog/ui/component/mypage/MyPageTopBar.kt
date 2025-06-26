package com.tlog.ui.component.mypage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tlog.R


@Composable
fun MyPageTopBar(
    logoutClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 11.dp)
    ) {
//        Text(
//            text = "MY page",
//            fontFamily = MainFont,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.ExtraBold,
//            color = Color.White,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.align(Alignment.Center)
//        )

        Icon(
            painter = painterResource(R.drawable.ic_logout),
            contentDescription = "로그아웃",
            tint = Color.Unspecified,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable {
                    logoutClick()
                }
        )
    }
}