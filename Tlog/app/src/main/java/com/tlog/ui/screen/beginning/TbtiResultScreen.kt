package com.tlog.ui.screen.beginning

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.theme.MainColor
import com.tlog.ui.component.tbti.TbtiResultTopBar
import com.tlog.ui.style.Body1Regular
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.beginning.TbtiResultViewModel
import androidx.navigation.NavController

@Composable
fun TbtiResultScreen(
    tbtiResult: String, // ex) RENA
    tbtiResultCode: String, // ex) 10230203
    viewModel: TbtiResultViewModel = hiltViewModel(),
    //tbtiTestViewModel: TbtiTestViewModel = hiltViewModel(),
    //loginViewModel: LoginViewModel = hiltViewModel(),
    traitScores: Map<String, Int>, // ViewModel에서 전달받는 점수 맵
    navController: NavController
) {

    val scrollState = rememberScrollState()
    val leftLabels = listOf("R", "E", "N", "A")
    val rightLabels = listOf("S", "O", "L", "I")
    val tbtiDescription = viewModel.tbtiDescription.value
    val tbtiCodeList = tbtiResultCode.toString().chunked(2)
    Log.d("resultCode11", tbtiResultCode)
    Log.d("resultCode11", tbtiCodeList.toString())



    LaunchedEffect(Unit) {
        viewModel.fetchTbtiDescription(tbtiResult)
    }

    if (tbtiDescription != null) {
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
                model = if (tbtiDescription.imageUrl.isNullOrBlank()) R.drawable.tbti_rena else tbtiDescription.imageUrl,
                contentDescription = "TBTI 캐릭터 이미지",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
                    .height(200.dp),
                //4contentScale = ContentScale.Fit
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

            tbtiCodeList.forEachIndexed { i, code ->
                val savedScore = 100 - code.toInt()

                val leftColor = if (savedScore >= 50) MainColor else Color.Black
                val rightColor = if (leftColor == MainColor) Color.Black else MainColor

                Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = leftLabels[i],
                            modifier = Modifier.padding(end = 10.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = MainFont,
                            color = leftColor
                        )
                        LinearProgressIndicator(
                            progress = savedScore / 100f,
                            modifier = Modifier
                                .weight(1f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = if (leftColor == MainColor) MainColor else Color(0xFFE0E0E0),
                            trackColor = if (leftColor == MainColor) Color(0xFFE0E0E0) else MainColor,
                            strokeCap = StrokeCap.Round
                        )
                        Text(
                            text = rightLabels[i],
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
                        text = "RENA",
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        color = MainColor
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    AsyncImage(
                        model = R.drawable.tbti_roli,
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
                        model = R.drawable.tbti_roli,
                        contentDescription = "최악의 궁합",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            val tbtiValue = if (traitScores["R"] != 0) traitScores["R"].toString() else {"00"} +
                    if (traitScores["E"] != 0) traitScores["E"].toString() else {"00"} +
                    if (traitScores["N"] != 0) traitScores["N"].toString() else {"00"} +
                    if (traitScores["A"] != 0) traitScores["A"].toString() else "00"


            MainButton(
                text = if (viewModel.isUserId()) "변경하기" else "시작하기",
                onClick = {
                    if (viewModel.isUserId()) {
                        viewModel.updateTbti(tbtiValue)
                        navController.popBackStack()
                        navController.popBackStack()
                        navController.navigate("myPage")
                    }
                    else
                        viewModel.registerUser(navController, tbtiValue)
                },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(29.dp))
        }
    }
}
