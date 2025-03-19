package com.tlog.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.component.MainButton


@Preview
@Composable
fun TbtiResultScreen() {
    val scrollState = rememberScrollState()

    val leftLabels = listOf("S", "L", "E", "A")
    val rightLabels = listOf("R", "O", "N", "I")
    val leftProgress = listOf(0.5f, 0.7f, 0.6f, 0.4f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 상단 네비게이션 바
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 45.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_left_arrow),
                contentDescription = "뒤로가기",
                modifier = Modifier
                    .width(42.dp)
                    .height(42.dp)
            )

            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_download),
                    contentDescription = "저장",
                    modifier = Modifier
                        .width(42.dp)
                        .height(42.dp)
                        .padding(end = 12.dp),
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = "공유",
                    modifier = Modifier
                        .width(42.dp)
                        .height(42.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Image(
            painter = painterResource(id = R.drawable.test_image),
            contentDescription = "TBTI 캐릭터 이미지",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "RENA",
            fontFamily = MainFont,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold)
        Text(text = "안정적인 자연 탐험가",
            fontFamily = MainFont,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
            )

        Spacer(modifier = Modifier.height(16.dp))

        // 프로그레스 바
        for (i in leftLabels.indices) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 23.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = leftLabels[i],
                    modifier = Modifier.padding(end = 10.dp),
                    fontFamily = MainFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )

                LinearProgressIndicator(
                    progress = { leftProgress[i] },
                    modifier = Modifier
                        .width(236.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color.Blue,
                    trackColor = Color(0xFFE0E0E0)
                )

                Text(
                    text = rightLabels[i],
                    modifier = Modifier.padding(start = 10.dp),
                    fontFamily = MainFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "RENA는 자연을 좋아하고 안정적인 계획을 선호하는 유형입니다. 여행에서도 효율성과 여유를 동시에 추구하며RENA는 자연을 좋아하고 안정적인 계획을 선호하는 유형입니다. 여행에서도 효율성과 여유를 동시에 추구하며" +
                    "RENA는 자연을 좋아하고 안정적인 계획을 선호하는 유형입니다. 여행에서도 효율성과 여유를 동시에 추구하며RENA는 자연을 좋아하고 안정적인 계획을 선호하는 유형입니다. 여행에서도 효율성과 여유를 동시에 추구하며" +
                    "RENA는 자연을 좋아하고 안정적인 계획을 선호하는 유형입니다. 여행에서도 효율성과 여유를 동시에 추구하며RENA는 자연을 좋아하고 안정적인 계획을 선호하는 유형입니다. 여행에서도 효율성과 여유를 동시에 추구하며" +
                    "RENA는 자연을 좋아하고 안정적인 계획을 선호하는 유형입니다. 여행에서도 효율성과 여유를 동시에 추구하며RENA는 자연을 좋아하고 안정적인 계획을 선호하는 유형입니다. 여행에서도 효율성과 여유를 동시에 추구하며" +
                    "RENA는 자연을 좋아하고 안정적인 계획을 선호하는 유형입니다. 여행에서도 효율성과 여유를 동시에 추구하며RENA는 자연을 좋아하고 안정적인 계획을 선호하는 유형입니다. 여행에서도 효율성과 여유를 동시에 추구하며" +
                    "RENA는 자연을 좋아하고 안정적인 계획을 선호하는 유형입니다. 여행에서도 효율성과 여유를 동시에 추구하며RENA는 자연을 좋아하고 안정적인 계획을 선호하는 유형입니다. 여행에서도 효율성과 여유를 동시에 추구하며" +
                    "RENA는 자연을 좋아하고 안정적인 계획을 선호하는 유형입니다. 여행에서도 효율성과 여유를 동시에 추구하며RENA는 자연을 좋아하고 안정적인 계획을 선호하는 유형입니다. 여행에서도 효율성과 여유를 동시에 추구하며",
            fontFamily = MainFont,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 잘 맞는 / 안 맞는 TBTI 카드
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF1F4FD))
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("잘 맞는 TBTI",
                    fontFamily = MainFont,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal)
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.test_image),
                    contentDescription = "잘 맞는 TBTI",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("RENA",
                    fontFamily = MainFont,
                    fontSize = 14.sp
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF1F4FD))
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("안 맞는 TBTI",
                    fontFamily = MainFont,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal)
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.test_image),
                    contentDescription = "안 맞는 TBTI",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("SALI",
                    fontFamily = MainFont,
                    fontSize = 14.sp
                    )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        MainButton(
            text = "Tlog 시작하기",
            onClick = { /* 메인 화면 이동 처리 */ },
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 50.dp)
                .height(65.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}
