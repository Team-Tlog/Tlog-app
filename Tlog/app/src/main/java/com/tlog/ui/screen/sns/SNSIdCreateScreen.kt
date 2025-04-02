package com.tlog.ui.screen.sns

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.team.TeamNameInputField
import com.tlog.ui.component.share.TopBar
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.sns.SNSIdViewModel

@Preview(showBackground = true)
@Composable
fun SNSIdCreateScreen(viewModel: SNSIdViewModel = viewModel()) {
    val id by viewModel.id.collectAsState()
    val isDuplicated by viewModel.isDuplicated.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        TopBar(text = "SNS 생성")

        Spacer(modifier = Modifier.height(35.dp))

        TeamNameInputField(
            text = "사용할 ID를 정해주세요",
            value = id,
            onValueChange = {
                viewModel.updateId(it)
                Log.d("SNS ID 입력", it)
            },
            placeholderText = "입력해주세요"
        )

        // 중복 여부 메시지 표시
        when (isDuplicated) {
            true -> {
                if (id.isNotEmpty()) {
                    Text(
                        text = "중복된 아이디입니다",
                        color = Color.Red,
                        modifier = Modifier
                            .padding(top = 43.dp)
                            .fillMaxWidth(),
                        fontFamily = MainFont,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            }
            false -> {
                if (id.isNotEmpty()) {
                    Text(
                        text = "사용 가능한 ID입니다!",
                        color = Color(0xFF4CAF50),
                        modifier = Modifier
                            .padding(top = 43.dp)
                            .fillMaxWidth(),
                        fontFamily = MainFont,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            }
            null -> {
                // 아무 것도 표시하지 않음
            }
        }


        Spacer(modifier = Modifier.weight(1f))

        // 중복이 없고, 값이 있을 때만 버튼 활성화
        if (id.isNotEmpty() && isDuplicated == false) {
            MainButton(
                text = "다음",
                onClick = {
                    Log.d("SNS ID 생성", "입력한 ID: $id")
                    // 이후 api 연동
                },
                modifier = Modifier
                    .height(70.dp)
                    .padding(start = 10.dp, end = 10.dp, bottom = 15.dp)
            )
        }
    }
}
