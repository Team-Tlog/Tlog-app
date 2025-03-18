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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
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
fun TbtiResultScreen(
    leftLabels: List<String> = listOf("S", "L", "E", "A"),
    rightLabels: List<String> = listOf("R", "O", "N", "I"),
    leftProgress: List<Float> = listOf(0.5f, 0.7f, 0.6f, 0.4f),
    rightProgress: List<Float> = listOf(0.6f, 0.3f, 0.8f, 0.9f)

) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        // 상단 대표 이미지
        Image(
            painter = painterResource(id = R.drawable.test_image), // 직접 넣은 이미지 사용
            contentDescription = "TBTI 대표 이미지",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 결과 텍스트
        Text(text = "RENA", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text(text = "안정적인 자연 탐험가", fontSize = 18.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        //각각 성향의 퍼센트를 나타내는 부분
        for (i in 0 until 4) {
            val maxProgress = maxOf(leftProgress[i], rightProgress[i])
            val animatedProgress by animateFloatAsState(
                targetValue = maxProgress,
                animationSpec = tween(800),
                label = "animated-bar-$i"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(leftLabels[i]) // 왼쪽 라벨

                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color.Blue,
                    trackColor = Color(0xFFE0E0E0)
                )

                Text(rightLabels[i]) // 오른쪽 라벨
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // 설명 텍스트
        Text(
            text = "RENA는 00을 중요하게 생각하고, 여행할 땐 자연을 좋아해요. 그래서 RENA는 00을 중요하게 생각하고, 여행할 땐 자연을 좋아해요. 그래서" +
                    "RENA는 00을 중요하게 생각하고, 여행할 땐 자연을 좋아해요. 그래서RENA는 00을 중요하게 생각하고, 여행할 땐 자연을 좋아해요. 그래서" +
                    "RENA는 00을 중요하게 생각하고, 여행할 땐 자연을 좋아해요. 그래서RENA는 00을 중요하게 생각하고, 여행할 땐 자연을 좋아해요. 그래서" +
                    "RENA는 00을 중요하게 생각하고, 여행할 땐 자연을 좋아해요. 그래서RENA는 00을 중요하게 생각하고, 여행할 땐 자연을 좋아해요. 그래서" +
                    "RENA는 00을 중요하게 생각하고, 여행할 땐 자연을 좋아해요. 그래서RENA는 00을 중요하게 생각하고, 여행할 땐 자연을 좋아해요. 그래서" +
                    "RENA는 00을 중요하게 생각하고, 여행할 땐 자연을 좋아해요. 그래서RENA는 00을 중요하게 생각하고, 여행할 땐 자연을 좋아해요. 그래서",
            fontSize = 14.sp,
            color = Color.DarkGray,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 잘 맞는 / 안 맞는 TBTI + 이미지
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // 잘 맞는 TBTI 카드
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF5F5F5))
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("잘 맞는 TBTI", fontWeight = FontWeight.Medium)
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
                Text("RENA")
            }

            // 안 맞는 TBTI 카드
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF5F5F5))
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("안 맞는 TBTI", fontWeight = FontWeight.Medium)
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
                Text("SALI")
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        // 시작 버튼
        MainButton(
            text = "Tlog 시작하기",
            onClick = { /* 메인화면으로 넘어가도록 */ }
        )
        Spacer(modifier = Modifier.height(5.dp))
    }
}
