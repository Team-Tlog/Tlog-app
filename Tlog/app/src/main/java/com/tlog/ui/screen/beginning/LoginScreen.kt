package com.tlog.ui.screen.beginning

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import com.tlog.viewmodel.beginning.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavController,
    onGoogleLoginClick: () -> Unit
) {
    val context = LocalContext.current

    var titleVisible by remember { mutableStateOf(false) }
    val bounceOffset = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        titleVisible = true

        viewModel.eventFlow.collect { event ->
            when (event) {
                LoginViewModel.UiEvent.LoginSuccess ->  {
                    Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT).show()
                    navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                    }
                }
                LoginViewModel.UiEvent.NewUser -> navController.navigate("tbtiIntro")
                is LoginViewModel.UiEvent.LoginFailure -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(titleVisible) {
        if (titleVisible) {
            // 위로 바운스 하는 느낌으로 끌어 올리고
            bounceOffset.animateTo(
                targetValue = -12f,
                animationSpec = tween(700, easing = FastOutSlowInEasing)
            )
            // 다시 원상복구 하기
            bounceOffset.animateTo(
                targetValue = 0f,
                animationSpec = tween(500, easing = LinearOutSlowInEasing)
            )
        }
    }

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

            AnimatedVisibility(
                visible = titleVisible,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(
                        durationMillis = 520,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(750)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(124.dp)
                        .padding(start = 40.dp, end = 40.dp, bottom = 12.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "여행을 \n담다",
                            style = TextStyle(
                                fontFamily = MainFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 40.sp,
                                lineHeight = 50.sp,
                                color = Color.White
                            ),
                            modifier = Modifier
                                .offset(y = bounceOffset.value.dp)
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.weight(1f))

            LoginBubble()

            Spacer(modifier = Modifier.height(22.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(35.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LoginIcon(R.drawable.login_ic_google, "구글", 50.dp) {
                    onGoogleLoginClick()
                }
                LoginIcon(R.drawable.login_ic_naver, "네이버", 50.dp) {
                    viewModel.naverLogin(context)
                }
                LoginIcon(R.drawable.login_ic_kakao, "카카오", 50.dp) {
                    viewModel.kakaoLogin(context)
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                modifier = Modifier
                    .clickable { /* 게스트 시작하기 */ }
                    .padding(bottom = 50.dp),
                text = "게스트로 시작하기",
                color = Color.White,
                fontFamily = MainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}
