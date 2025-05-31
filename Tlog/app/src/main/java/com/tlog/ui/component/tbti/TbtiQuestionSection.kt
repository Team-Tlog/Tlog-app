package com.tlog.ui.component.tbti

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainFont
import com.tlog.ui.component.share.MainButton
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.viewmodel.beginning.TbtiTestViewModel

@Composable
fun TbtiQuestionSection(
    questionNumber: Int = 1,
    totalQuestions: Int = 10,
    viewModel: TbtiTestViewModel = viewModel()
) {
    // 선택지 선택 상태 관리
    var selectedIdx by remember { mutableStateOf<Int?>(null) }

    val question = if (questionNumber == 1) "여행을 가기 전날 밤 내 가방의 상태는?" else viewModel.questionList.getOrNull(questionNumber - 1) ?: ""
    val chooseText1 = if (questionNumber == 1) "이미 갈 준비 완료! 편하게 자볼까?" else viewModel.answerLists.getOrNull(questionNumber - 1)?.getOrNull(0) ?: ""
    val chooseText2 = if (questionNumber == 1) "벌써 내일이야? 빨리 짐 싸자!!" else viewModel.answerLists.getOrNull(questionNumber - 1)?.getOrNull(1) ?: ""

    androidx.compose.ui.platform.LocalConfiguration.current.screenWidthDp.dp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 질문 카운터 (Q1 텍스트 대신 1/10)
        Text(
            text = "$questionNumber/$totalQuestions",
            fontSize = 18.sp,
            fontFamily = MainFont,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TbtiProgressBar(
            current = questionNumber,
            total = totalQuestions,
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 질문 텍스트
        Text(
            text = question,
            fontSize = 24.sp,
            fontFamily = MainFont,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 20.dp)
                .width(200.dp)
                .height(65.dp)
        )

        Spacer(modifier = Modifier.height(70.dp))

        // 선택지 2개
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TbtiTestAnswerBox(
                selectIdx = 1,
                answer = chooseText1,
                selected = selectedIdx == 1,
                onClick = {
                    selectedIdx = 1
                    viewModel.addList(1)
                }
            )
            TbtiTestAnswerBox(
                selectIdx = 2,
                answer = chooseText2,
                selected = selectedIdx == 2,
                onClick = {
                    selectedIdx = 2
                    viewModel.addList(2)
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        MainButton(
            text = "다음",
            enabled = selectedIdx != null,
            onClick = { /* TODO: 다음 질문으로 이동 */ },
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )
    }
}