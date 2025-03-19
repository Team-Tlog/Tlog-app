package com.tlog.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont


@Preview
@Composable
fun TbtiTestScreen(
    current: Int = 1, // 질문 번호
    total: Int = 10, // 총 질문 개수
    question: String = "여행을 가기 전날 밤\n내 가방의 상태는?", // 질문
    chooseText1: String = "이미 갈 준비 완료!\n편하게 자볼까?", // 선택지 1
    chooseText2: String = "벌써 내일이야?\n빨리 짐싸자!!" // 선택지 2
) {
    val progress = current.toFloat() / total.toFloat()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ){
            val topPadding = maxHeight * 0.08f
            val sidePadding = maxWidth * 0.05f
            val questionTopPadding = maxHeight * 0.07f
            val questionTextTopPadding = maxHeight * 0.06f
            val midPadding = maxHeight * 0.07f
            val boxPadding = maxWidth * 0.15f
            val boxVerPadding = maxHeight * 0.05f
            val vsVerPadding = maxHeight * 0.02f

            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(topPadding))

                LinearProgressIndicator( // 상단 진행률 표시해주는 바
                    progress = progress,
                    color = MainColor,
                    trackColor = Color(0xFFF0F0F0),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .padding(horizontal = sidePadding)
                        .clip(RoundedCornerShape(50))
                )

                Spacer(modifier = Modifier.height(5.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = sidePadding)
                ) {
                    Text(
                        text = "$current/$total",
                        fontSize = 11.sp,
                        color = Color(0xFFC4C4C4),
                        modifier = Modifier.align(Alignment.CenterEnd) // Alignment.Horizontal 후 사용 시 CenterEnd 사용 시 Box로
                    )
                }

                Spacer(modifier = Modifier.height(questionTopPadding))

                Text(
                    text = "Q.",
                    color = MainColor,
                    fontSize = 47.sp,
                    fontFamily = MainFont,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(questionTextTopPadding))

                Text(
                    text = question,
                    fontSize = 27.sp,
                    fontFamily = MainFont,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(midPadding))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = boxPadding)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color(0xFFF1F4FD))
                        .clickable {
                            Log.d("choose1", "my click!!") // 추후 로직 만들 때 호출 매개변수로 받아야 될 듯!!
                        }
                        .padding(vertical = boxVerPadding)
                ) {
                    Text(
                        text = chooseText1,
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

                Text(
                    text = "VS",
                    fontSize = 30.sp,
                    color = MainColor,
                    fontFamily = MainFont,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = vsVerPadding)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = boxPadding)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color(0xFFF1F4FD))
                        .clickable {
                            Log.d("choose2", "my click!!")
                        }
                        .padding(vertical = boxVerPadding)
                ) {
                    Text(
                        text = chooseText2,
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}