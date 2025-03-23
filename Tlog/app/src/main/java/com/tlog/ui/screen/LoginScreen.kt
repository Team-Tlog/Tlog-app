package com.tlog.ui.screen

import android.util.Log
import androidx.compose.foundation.background
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
import com.tlog.R
import com.tlog.ui.component.LoginIcon
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont


@Preview
@Composable
fun LoginScreen() {
    Surface (
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

//               Text(
//                   text = "TBTI 기반으로 AI에게 여행지 추천을 받아보세요.",
//                   fontFamily = MainFont,
//                   fontWeight = FontWeight.Medium,
//                   fontSize = 12.sp,
//                   color = Color.White
//               )

           Spacer(modifier = Modifier.weight(1f)) // 남은 공간 아래로 밀어버리기

           Row(
               horizontalArrangement = Arrangement.spacedBy(35.dp),
               verticalAlignment = Alignment.CenterVertically
           ) {
               // {} 사이에 실행 로직 넣을 것
               LoginIcon(R.drawable.naver_login_icon, "네이버", 50.dp) {
                   Log.d(
                       "NaverLoginIcon",
                       "my click!!"
                   )
               }
               LoginIcon(R.drawable.google_login_icon, "구글", 50.dp) {
                   Log.d(
                       "GoogleLoginIcon",
                       "my click!!"
                   )
               }
               LoginIcon(R.drawable.kakao_login_icon, "카카오", 50.dp) {
                   Log.d(
                       "KakaoLoginIcon",
                       "my click!!"
                   )
               } // 카카오톡 로그인 사진 X -> 카카오 로그인 사진으로 변경하면 베스트
           }

           Spacer(modifier = Modifier.height(22.dp))

           Text(
               modifier = Modifier
                   .padding(bottom = 64.dp)
                   .clickable { Log.d("GuestStart", "my click!!") },
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

