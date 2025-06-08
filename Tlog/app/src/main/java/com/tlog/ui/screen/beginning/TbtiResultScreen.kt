package com.tlog.ui.screen.beginning

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.data.api.TbtiDescriptionResponse
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.theme.MainColor
import com.tlog.ui.component.tbti.TbtiResultTopBar
import com.tlog.ui.style.Body1Regular
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.beginning.TbtiTestViewModel

@Composable
fun TbtiResultScreen(
    tbtiResultCode: String,
    viewModel: TbtiTestViewModel,
    tbtiDescription: TbtiDescriptionResponse,
    traitScores: Map<Char, Float> // ViewModel에서 전달받는 점수 맵
) {
    val scrollState = rememberScrollState()
    val leftLabels = listOf('S', 'E', 'L', 'A')
    val rightLabels = listOf('R', 'O', 'N', 'I')

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

        AsyncImage(
            model = if (tbtiDescription.imageUrl.isNullOrBlank()) R.drawable.test_image else tbtiDescription.imageUrl,
            contentDescription = "TBTI 캐릭터 이미지",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = tbtiDescription.tbtiString,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = MainFont
        )
        Text(
            text = tbtiDescription.secondName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = MainFont,
            color = Color(0xFF767676)
        )

        Spacer(modifier = Modifier.height(16.dp))

        for (i in leftLabels.indices) {
            val leftScore = traitScores[leftLabels[i]] ?: 0f
            val rightScore = traitScores[rightLabels[i]] ?: 0f

            val selectedAlphabet = tbtiResultCode.getOrNull(i) ?: ' '

            val leftColor = if (selectedAlphabet == leftLabels[i]) MainColor else Color.Black
            val rightColor = if (selectedAlphabet == rightLabels[i]) MainColor else Color.Black

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = leftLabels[i].toString(),
                    modifier = Modifier.padding(end = 10.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = MainFont,
                    color = leftColor
                )
                LinearProgressIndicator(
                    progress = (leftScore / 100f).coerceIn(0f, 1f),
                    modifier = Modifier
                        .width(260.dp)
                        .height(6.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = MainColor,
                    trackColor = Color(0xFFE0E0E0)
                )
                Text(
                    text = rightLabels[i].toString(),
                    modifier = Modifier.padding(start = 10.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = MainFont,
                    color = rightColor
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = tbtiDescription?.description ?: "",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 20.sp,
            fontFamily = MainFont,
            modifier = Modifier
                .padding(horizontal = 24.dp),
            color = Color(0xFF767676)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 궁합 정보: tbtiDescription에서 정보를 받아온다고 가정 (예시: bestMatch, bestMatchImageUrl, worstMatch, worstMatchImageUrl)
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
                    .padding(17.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "최고의 궁합",
                    style = Body1Regular,
                    color = Color(0xFF767676)
                )
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text ="RENA",
                    fontFamily = MainFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = MainColor
                )

                Spacer(modifier = Modifier.height(5.dp))

                    AsyncImage(
                        model = R.drawable.test_image,
                        contentDescription = "최고의 궁합",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
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
                Text(
                    text = "최악의 궁합",
                    style = Body1Regular,
                    color = Color(0xFF767676)
                )
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "RENA",
                    fontFamily = MainFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = MainColor
                )

                Spacer(modifier = Modifier.height(5.dp))

                    AsyncImage(
                        model = R.drawable.test_image,
                        contentDescription = "최악의 궁합",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
        }

        Spacer(modifier = Modifier.height(30.dp))

        MainButton(
            text = "시작하기",
            onClick = { /* 메인 화면 이동 */ },
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(29.dp))
    }
}
