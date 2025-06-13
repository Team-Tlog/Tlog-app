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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.viewmodel.beginning.TbtiTestViewModel
import androidx.compose.foundation.layout.imePadding

@Composable
fun TbtiQuestionSection(
    question: String?,
    answers: List<String>,
    viewModel: TbtiTestViewModel = viewModel()
) {
    val questionNumber = viewModel.currentQuestionIndex.value + 1
    val totalQuestions = viewModel.totalQuestions
    val selectedIndex = viewModel.selectedIdx.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 0.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

        Text(
            text = question.toString(),
            fontSize = 24.sp,
            fontFamily = MainFont,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 20.dp)
                .width(230.dp)
        )

        Spacer(modifier = Modifier.height(70.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = (23.5).dp, end = (23.5).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            answers.getOrNull(0)?.let { answer1 ->
                TbtiTestAnswerBox(
                    viewModel = viewModel,
                    selectIdx = 1,
                    answer = answer1,
                    selected = selectedIndex == 1,
                    onClick = { viewModel.onAnswerSelected(1) }
                )
            }
            answers.getOrNull(1)?.let { answer2 ->
                TbtiTestAnswerBox(
                    viewModel = viewModel,
                    selectIdx = 2,
                    answer = answer2,
                    selected = selectedIndex == 2,
                    onClick = { viewModel.onAnswerSelected(2) }
                )
            }
        }
    }
}