package com.tlog.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.component.MainButton
import com.tlog.ui.theme.MainColor
import com.tlog.ui.component.TbtiResultTopBar
import com.tlog.ui.theme.MainFont

fun parseTbtiCode(code: String): Map<String, Float> {
    val labelPairs = listOf("S" to "R", "E" to "O", "L" to "N", "A" to "I")
    if (code.length != 8 || !code.all { it.isDigit() }) {
        throw IllegalArgumentException("8자리 숫자여야 합니다.")
    }
    val map = mutableMapOf<String, Float>()
    for ((index, pair) in labelPairs.withIndex()) {
        val left = pair.first
        val right = pair.second
        val valueStr = code.substring(index * 2, index * 2 + 2)
        val value = valueStr.toInt().coerceIn(0, 100)
        map[left] = value / 100f
        map[right] = (100 - value) / 100f
    }
    return map
}

fun getOrderedTopLetters(code: String): String {
    val labelPairs = listOf("S" to "R", "E" to "O", "L" to "N", "A" to "I")
    val tbtiMap = parseTbtiCode(code)
    return labelPairs.joinToString("") { (first, second) ->
        val firstScore = tbtiMap[first] ?: 0f
        val secondScore = tbtiMap[second] ?: 0f
        if (firstScore >= secondScore) first else second
    }
}

fun getDescriptionFromTopLetters(top: String): String {
    return when (top) {
        "RENA" -> "안정적인 자연 탐험가"
        "SLAI" -> "도전적인 도시 탐험가"
        "SOLE" -> "즉흥적인 자유 여행자"
        "LENA" -> "감성적인 힐링 여행가"
        else -> "당신만의 특별한 여행 스타일"
    }
}

@Preview(showBackground = true)
@Composable
fun TbtiResultScreen(
    code: String = "12733967"
) {
    val scrollState = rememberScrollState()
    val tbtiMap = parseTbtiCode(code)
    val topLetters = getOrderedTopLetters(code)
    val description = getDescriptionFromTopLetters(topLetters)
    val leftLabels = listOf("S", "E", "L", "A")
    val rightLabels = listOf("R", "O", "N", "I")
    val progress = leftLabels.map { tbtiMap[it] ?: 0f }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(horizontal = 14.dp)
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TbtiResultTopBar(
            onDownloadClick = { /*다운로드하기*/ },
            onShareClick = { /*공유하기*/ }
        )

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

        Text(
            text = topLetters,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = MainFont
        )
        Text(
            text = description,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = MainFont
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                    modifier = Modifier.padding(end = 6.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = MainFont
                )
                LinearProgressIndicator(
                    progress = { progress[i] },
                    modifier = Modifier
                        .width(236.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = MainColor,
                    trackColor = Color(0xFFE0E0E0)
                )
                Text(
                    text = rightLabels[i],
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = MainFont
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "${topLetters}는 여행에서도 효율성과 여유를 동시에 추구하는 타입입니다.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 20.sp,
            fontFamily = MainFont,
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

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
                Text("잘 맞는 TBTI", fontFamily = MainFont)
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
                Text("RENA", fontFamily = MainFont)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF1F4FD))
                    .padding(17.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("안 맞는 TBTI", fontFamily = MainFont)
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
                Text("SALI", fontFamily = MainFont)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        MainButton(
            text = "Tlog 시작하기",
            onClick = { /* 메인 화면 이동 */ },
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp)
                .height(65.dp)
        )

        Spacer(modifier = Modifier.height(29.dp))
    }
}
