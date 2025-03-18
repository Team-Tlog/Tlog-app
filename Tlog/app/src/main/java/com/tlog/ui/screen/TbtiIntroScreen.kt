package com.tlog.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.component.MainButton
import com.tlog.ui.theme.FontBlue

@Composable
fun TbtiIntroScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            // 사용자 화면 크기에 반응하기 위해 값을 설정 후 n(숫자).dp 대신 사용
            val topPadding = maxHeight * 0.18f
            val midPadding = maxHeight * 0.015f
            val bottomPadding = maxHeight * 0.08f
            val sidePadding = maxWidth * 0.07f

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = topPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "TBTI란?",
                        color = Color.Black,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(midPadding))

                    Text(
                        text = "여행 성향을 분석해 나만의 여행 유형을",
                        color = Color.Black,
                        fontSize = 15.sp,
                    )

                    Text(
                        text = "찾아주는 맞춤형 여행 성향 테스트",
                        color = Color.Black,
                        fontSize = 15.sp,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                MainButton(
                    text = "테스트 시작",
                    onClick = {Log.d("TbtiTestStartButton", "my click!!")},
                    modifier = Modifier
                        .padding(horizontal = sidePadding)
                        .fillMaxWidth()
                        .height(65.dp)
                )

                Spacer(modifier = Modifier.height(midPadding))

                Row(
                    horizontalArrangement =  Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = bottomPadding)
                ) {
                    Text(
                        text = "이미 테스트를 진행하셨나요?",
                        fontSize = 14.sp
                    )

                    Text(
                        text = "건너뛰기",
                        fontSize = 14.sp,
                        color = FontBlue,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable{ Log.d("TbtiSkipText", "my click!!") }
                    )
                }
            }
        }

    }
}