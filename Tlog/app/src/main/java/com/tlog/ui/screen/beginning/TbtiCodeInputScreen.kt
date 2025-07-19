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
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.share.OtpCodeInput
import com.tlog.ui.style.SubTitle
import com.tlog.ui.theme.FontBlue
import com.tlog.ui.theme.MainFont
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.tlog.viewmodel.beginning.TbtiCodeInputViewModel
import com.tlog.R


@Composable
fun TbtiCodeInputScreen(
    viewModel: TbtiCodeInputViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current
    val codeError = viewModel.codeError
    val isCodeValid = viewModel.isCodeValid
    val textList = viewModel.textList
    val requesterList = viewModel.requesterList

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        color = Color.White

    ) {
        val density = LocalDensity.current
        val imeBottom = WindowInsets.ime.getBottom(density)
        val isKeyboardVisible = imeBottom > 0

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(157.dp))
            Text(
                text = "TBTI 코드 입력",
                modifier = Modifier
                    .align (Alignment.CenterHorizontally),
                style = SubTitle
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "테스트 완료 후 받으신\n인증번호 8자리를 입력해주세요",
                modifier = Modifier
                    .width(182.dp)
                    .height(40.dp)
                    .align (Alignment.CenterHorizontally),
                fontFamily = MainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0xFF767676),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(50.dp))

            LaunchedEffect(textList.map { it.value.text }) {
                viewModel.checkCodeValid()
            }

            OtpCodeInput(
                textList = textList,
                requesterList = requesterList,
                onComplete = { code ->
                    viewModel.onCodeEntered(code)
                    if (viewModel.isCodeValid.value) {
                        Log.d("TbtiCode", "success$code")
                        focusManager.clearFocus()
                    } else {
                        Log.d("TbtiCode", "fail$code")
                    }
                }
            )

            Spacer(modifier = Modifier.height(37.dp))

            if (codeError.value) {
                LaunchedEffect(key1 = codeError.value) {}
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_alert_red),
                        contentDescription = "오류 아이콘",
                        modifier = Modifier
                            .size(15.dp)
                            .padding(end = 10.dp),
                        tint = Color.Unspecified // 원본 색상 유지
                    )
                    Text(
                        text = "올바른 코드를 입력해주세요.",
                        fontFamily = MainFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MainButton(
                    text = "확인",
                    enabled = isCodeValid.value,
                    onClick = {
                        Log.d("resultButton", "my click!!")
                    },
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                // 키보드 상태에 따라 Row 분기
                if (isKeyboardVisible) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(bottom = 37.dp)
                    ) {
                        Text(
                            text = "결과를 잊어버리셨나요?",
                            fontFamily = MainFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "테스트 다시하기",
                            fontFamily = MainFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            textDecoration = TextDecoration.Underline,
                            color = FontBlue,
                            modifier = Modifier
                                .clickable { Log.d("reTest", "my click!!") }
                        )
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(bottom = 37.dp)
                    ) {
                        Text(
                            text = "이미 테스트를 진행하셨나요?",
                            fontFamily = MainFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "건너뛰기",
                            fontFamily = MainFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            textDecoration = TextDecoration.Underline,
                            color = FontBlue,
                            modifier = Modifier
                                .clickable { Log.d("skip", "my click!!") }
                        )
                    }
                }
            }
        }
    }
}
