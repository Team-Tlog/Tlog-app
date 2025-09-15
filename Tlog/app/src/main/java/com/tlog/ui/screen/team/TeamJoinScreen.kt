package com.tlog.ui.screen.team

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.share.OtpCodeInput
import com.tlog.ui.style.SubTitle
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.team.TeamJoinViewModel
import com.tlog.viewmodel.base.BaseViewModel.UiEvent


@Composable
fun TeamJoinScreen(
    viewModel: TeamJoinViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val codeError = viewModel.codeError
    val isCodeValid = viewModel.isCodeValid
    val textList = viewModel.textList
    val requesterList = viewModel.requesterList

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> Unit
                is UiEvent.PopBackStack -> {
                    repeat(event.count) {
                        navController.popBackStack()
                    }
                }
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = (24.5).dp) // 피그마에 없어서 일단 아래 여백을 좀 뒀음
                .imePadding(), // 키보드 패딩
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(157.dp))

            Text(
                text = "팀 코드 입력",
                style = SubTitle
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "전달 받으신 팀코드\n 6자리를 입력해주세요.",
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
                isNumber = false,
                onComplete = { code ->
                    viewModel.onCodeEntered(code)
                    if (viewModel.isCodeValid.value) {
                        focusManager.clearFocus()
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (codeError.value) {
                LaunchedEffect(key1 = codeError.value) {}
                Text(
                    text = "올바른 코드를 입력해주세요.",
                    fontFamily = MainFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Red
                )
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
                        viewModel.joinTeam()
                    },
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                )
            }
        }
    }
}