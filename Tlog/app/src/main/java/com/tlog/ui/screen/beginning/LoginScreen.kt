package com.tlog.ui.screen.beginning

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.R
import com.tlog.ui.component.login.LoginIcon
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.api.beginning.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor)
            .windowInsetsPadding(WindowInsets.systemBars),
        color = MainColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 144.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(start = 70.dp),
                    text = "여행을",
                    fontSize = 40.sp,
                    fontFamily = MainFont,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Text(
                    modifier = Modifier.padding(start = 70.dp),
                    text = "담다",
                    fontSize = 40.sp,
                    fontFamily = MainFont,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.spacedBy(35.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LoginIcon(R.drawable.naver_login_icon, "네이버", 50.dp) {
                    // 나중에 추가
                }
                LoginIcon(R.drawable.google_login_icon, "구글", 50.dp) {
                    // 나중에 추가
                }
                LoginIcon(R.drawable.kakao_login_icon, "카카오", 50.dp) {
                    viewModel.kakaoLogin(context)
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                modifier = Modifier
                    .padding(bottom = 64.dp)
                    .clickable { /* 게스트 시작하기 */ },
                text = "게스트로 시작하기",
                color = Color.White,
                fontFamily = MainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}
