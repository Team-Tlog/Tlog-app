package com.tlog.ui.screen.share

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.theme.MainFont

@Preview(showBackground = true)
@Composable
fun ErrorScreen(
    massage: String = "잠시 후 다시 시도해주세요."
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.character_error),
                contentDescription = "에러 캐릭터 이미지",
                modifier = Modifier
                    .size(150.dp)
            )

            Spacer(modifier = Modifier.height(19.dp))

            Text(
                text = "로딩 실패",
                style = TextStyle(
                    fontFamily = MainFont,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )

            Text(
                text = massage,
                style = TextStyle(
                    fontFamily = MainFont,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            )
        }
    }
}