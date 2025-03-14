package com.tlog.ui.screen

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
       Column (
           modifier = Modifier.fillMaxSize(),
           horizontalAlignment = Alignment.CenterHorizontally
       )  {
           Column(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(top = 220.dp),
               horizontalAlignment = Alignment.Start
           ) {
               Text(
                   modifier = Modifier.padding(start = 45.dp),
                   text = "여행을",
                   fontSize = 42.sp,
                   fontWeight = FontWeight.Bold,
                   color = Color.White
               )

               Text(
                   modifier = Modifier.padding(start = 45.dp),
                   text = "담다",
                   fontSize = 42.sp,
                   fontWeight = FontWeight.Bold,
                   color = Color.White
               )
           }

           Spacer(modifier = Modifier.height(280.dp))

           Text(
               text = "TBTI 기반으로 AI에게 여행지 추천을 받아보세요.",
               fontSize = 14.sp,
               color = Color.White
           )

           Spacer(modifier = Modifier.height(25.dp))

           Row(
               horizontalArrangement = Arrangement.spacedBy(50.dp),
               verticalAlignment = Alignment.CenterVertically
           ) {
               // 추후 아이콘 화질 좋은 것으로 변경, 알맞게 변경
               LoginIcon(R.drawable.naver_icon, "네이버") { Log.d("NaverLoginIcon", "my click!!") }
               LoginIcon(R.drawable.naver_icon, "카카오") { Log.d("KakaoLoginIcon", "my click!!") }
               LoginIcon(R.drawable.naver_icon, "구글") { Log.d("GoogleLoginIcon", "my click!!") }
           }

           Spacer(modifier = Modifier.weight(1f)) // 남은 공간 아래로 밀어버리기

           Text (
               modifier = Modifier
                   .padding(bottom = 85.dp)
                   .clickable{ Log.d("GuestStart", "my click!!") },
               text = "게스트로 시작하기",
               color = Color.White,
               fontSize = 12.sp,
               textDecoration = TextDecoration.Underline
           )
       }
    }
}

