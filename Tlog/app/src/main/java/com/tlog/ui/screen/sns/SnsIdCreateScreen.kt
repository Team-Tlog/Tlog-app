package com.tlog.ui.screen.sns

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.share.TitleInputField
import com.tlog.ui.component.share.TopBar
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.sns.SNSIdViewModel
import com.tlog.viewmodel.sns.SNSIdViewModel.UiEvent
import com.tlog.R

@Composable
fun SnsIdCreateScreen(
    viewModel: SNSIdViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate("snsMain")
                }
                is UiEvent.ShowToast -> {
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

        Spacer(modifier = Modifier.height(74.dp))

        TitleInputField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = "사용할 ID를 정해주세요",
            value = viewModel.snsId.value,
            onValueChange = {
                viewModel.updateId(it)
            },
            placeholderText = "입력해주세요",
            spacerHeight = 50.dp
        )

        // 중복 여부 메시지 표시
            if (viewModel.isDuplicated.value == true) {
                Row(
                    modifier = Modifier
                        .padding(top = 43.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .clickable{}
                    )

                    Spacer(modifier = Modifier.width((2.5).dp))

                    Text(
                        text = "중복된 아이디입니다",
                        color = Color(0xFFFF5454),
                        fontFamily = MainFont,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }


        Spacer(modifier = Modifier.weight(1f))


        MainButton(
            text = "다음",
            onClick = {
                Log.d("SNS ID 생성", "입력한 ID: ${viewModel.snsId.value}")
                viewModel.updateSnsId(viewModel.snsId.value)
            },
            enabled = viewModel.snsId.value.isNotEmpty() && viewModel.snsId.value.length >= 3,
            modifier = Modifier
                .height(85.dp)
                .padding(start = 24.dp, end = 24.dp, top = 15.dp, bottom = 15.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "추후 변경이 가능합니다.",
            style = TextStyle(
                fontFamily = MainFont,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFA8A8A8)
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
    }
}
