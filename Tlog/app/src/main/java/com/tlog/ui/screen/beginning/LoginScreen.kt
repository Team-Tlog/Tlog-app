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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tlog.R
import com.tlog.ui.component.login.LoginBubble
import com.tlog.ui.component.login.LoginIcon
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.beginning.login.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavController,
    onGoogleLoginClick: () -> Unit
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
            Spacer(modifier = Modifier.height(288.dp))

            Box(
                modifier = Modifier
                    .width(360.dp)
                    .height(124.dp)
                    .padding(start = 40.dp, end = 40.dp, bottom = 12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .width(120.dp)
                            .height(100.dp),
                        text = "여행을 \n담다",
                        style = TextStyle(
                            fontFamily = MainFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                            lineHeight = 50.sp,
                            color = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(133.dp))

            LoginBubble()

            Spacer(modifier = Modifier.height(22.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(35.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LoginIcon(R.drawable.google_login_icon, "구글", 50.dp) {
                    onGoogleLoginClick()
                }
                LoginIcon(R.drawable.naver_login_icon, "네이버", 50.dp) {
                    viewModel.naverLogin(context, navController)
                }
                LoginIcon(R.drawable.kakao_login_icon, "카카오", 50.dp) {
                    viewModel.kakaoLogin(context, navController)
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                modifier = Modifier
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
