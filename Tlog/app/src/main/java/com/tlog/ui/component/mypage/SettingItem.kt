package com.tlog.ui.component.mypage

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.R
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.share.MyPageViewModel


@Composable
fun SettingItem(viewModel: MyPageViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "알림 설정",
                fontFamily = MainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(10.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .height(21.dp)
                    .width(42.dp)
            ) {
                Switch(
                    checked = viewModel.notification.value,
                    onCheckedChange = { viewModel.changeNotification() },
                    colors = SwitchDefaults.colors(
                        uncheckedTrackColor = Color(0xFFE1E1E1),
                        uncheckedThumbColor = Color.White,
                        checkedThumbColor = Color.White,
                        checkedTrackColor = MainColor,
                        uncheckedBorderColor = Color.Transparent
                    ),
                    modifier = Modifier // 고정된 크기라 scale로 비율 조정
                        .scale(0.656f)
                )
            }
        }

        val tmpList = listOf("개인정보처리방침", "고객센터", "개발자에게 피드백 해주기")

        tmpList.forEach { text ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        when (text) {
                            "개인정보처리방침" -> {
                                Log.d(text, "my click!!")
                            }

                            "고객센터" -> {
                                Log.d(text, "my click!!")
                            }

                            "개발자에게 피드백 해주기" -> {
                                Log.d(text, "my click!!")
                            }
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    fontFamily = MainFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(10.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(R.drawable.ic_right_arrow),
                    contentDescription = text,
                    tint = Color.Black
                )
            }
        }

        Text(
            text = "회원탈퇴",
            fontFamily = MainFont,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = Color.Red,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}