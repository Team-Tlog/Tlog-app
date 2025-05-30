package com.tlog.ui.screen.beginning

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.theme.FontBlue
import com.tlog.ui.theme.MainFont


@Preview
@Composable
fun TbtiIntroScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        color = Color.White
    ) {

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 263.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "TBTI를 아시나요?",
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontFamily = MainFont,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = "테스트하고 성격에 맞는 여행을 즐기러 가볼까요?",
                        color = Color(0xFF767676),
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "여행 성향을 분석해 나만의 여행 유형을\n찾아주는 맞춤형 여행 성향 테스트입니다",
                        color = Color(0xFF989898),
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                MainButton(
                    text = "테스트 시작",
                    onClick = {Log.d("TbtiTestStartButton", "my click!!")},
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .height(55.dp)
                )

                Spacer(modifier = Modifier.padding(bottom = 4.dp))

                Row(
                    horizontalArrangement =  Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 26.dp)
                ) {
                    Text(
                        text = "이미 테스트를 진행하셨나요?",
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "건너뛰기",
                        fontSize = 14.sp,
                        color = FontBlue,
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Normal,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .clickable{ Log.d("TbtiSkipText", "my click!!") }
                    )
                }
            }
        }

    }
}