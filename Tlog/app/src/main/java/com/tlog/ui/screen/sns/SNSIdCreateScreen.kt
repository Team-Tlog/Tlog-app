package com.tlog.ui.screen.sns

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.team.TeamNameInputField
import com.tlog.ui.component.share.TopBar
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.sns.SNSIdViewModel
import com.tlog.viewmodel.sns.SNSIdViewModel.UiEvent

@Composable
fun SNSIdCreateScreen(
    viewModel: SNSIdViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ApiSuccess -> {
                    navController.navigate("snsMain")
                }
                is UiEvent.ApiError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

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
            value = viewModel.snsId.value,
            onValueChange = {
                viewModel.updateId(it)
                Log.d("SNS ID 입력", it)
            },
            placeholderText = "입력해주세요"
        )

        // 중복 여부 메시지 표시
        when (viewModel.isDuplicated.value) {
            true -> {
                if (viewModel.snsId.value.isNotEmpty()) {
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
            else -> {
                // 아무 것도 표시하지 않음
            }
        }


        Spacer(modifier = Modifier.weight(1f))


        if (viewModel.snsId.value.isNotEmpty() && viewModel.snsId.value.length >= 3) {
            MainButton(
                text = "다음",
                onClick = {
                    Log.d("SNS ID 생성", "입력한 ID: ${viewModel.snsId.value}")
                    viewModel.updateSnsId(viewModel.snsId.value)
                },
                modifier = Modifier
                    .height(85.dp)
                    .padding(start = 10.dp, end = 10.dp, top = 15.dp, bottom = 15.dp)
            )
        }
    }
}
