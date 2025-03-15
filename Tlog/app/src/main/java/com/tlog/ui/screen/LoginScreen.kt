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
import com.tlog.R
import com.tlog.ui.component.LoginIcon
import com.tlog.ui.theme.MainColor


@Composable
fun LoginScreen() {
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MainColor
    ) {
       BoxWithConstraints (
           modifier = Modifier.fillMaxSize()
       ) {
           val topPadding = maxHeight * 0.20f
           val startPadding = maxWidth * 0.11f
           val midPadding = maxHeight * 0.35f
           val iconTopPadding = maxHeight * 0.04f
           val bottomPadding = maxHeight * 0.1f
           val iconSpace = maxWidth * 0.1f
           val iconSize = maxWidth * 0.14f

           Column(
               modifier = Modifier.fillMaxSize(),
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               Column(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(top = topPadding),
                   horizontalAlignment = Alignment.Start
               ) {
                   Text(
                       modifier = Modifier.padding(start = startPadding),
                       text = "여행을",
                       fontSize = 45.sp,
                       fontWeight = FontWeight.ExtraBold,
                       color = Color.White
                   )

                   Text(
                       modifier = Modifier.padding(start = startPadding),
                       text = "담다",
                       fontSize = 45.sp,
                       fontWeight = FontWeight.ExtraBold,
                       color = Color.White
                   )
               }

               Spacer(modifier = Modifier.height(midPadding))

               Text(
                   text = "TBTI 기반으로 AI에게 여행지 추천을 받아보세요.",
                   fontSize = 14.sp,
                   color = Color.White
               )

               Spacer(modifier = Modifier.height(iconTopPadding))

               Row(
                   horizontalArrangement = Arrangement.spacedBy(iconSpace),
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   // {} 사이에 실행 로직 넣을 것
                   LoginIcon(R.drawable.naver_login_icon, "네이버", iconSize) {
                       Log.d(
                           "NaverLoginIcon",
                           "my click!!"
                       )
                   }
                   LoginIcon(R.drawable.google_login_icon, "구글", iconSize) {
                       Log.d(
                           "GoogleLoginIcon",
                           "my click!!"
                       )
                   }
                   LoginIcon(R.drawable.kakao_login_icon, "카카오", iconSize) {
                       Log.d(
                           "KakaoLoginIcon",
                           "my click!!"
                       )
                   } // 카카오톡 로그인 사진 X -> 카카오 로그인 사진으로 변경하면 베스트
               }

               Spacer(modifier = Modifier.weight(1f)) // 남은 공간 아래로 밀어버리기

               Text(
                   modifier = Modifier
                       .padding(bottom = bottomPadding)
                       .clickable { Log.d("GuestStart", "my click!!") },
                   text = "게스트로 시작하기",
                   color = Color.White,
                   fontSize = 12.sp,
                   textDecoration = TextDecoration.Underline
               )
           }
       }
    }
}

