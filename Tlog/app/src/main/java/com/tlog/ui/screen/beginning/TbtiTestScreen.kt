package com.tlog.ui.screen.beginning

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.tbti.TbtiQuestionSection
import com.tlog.viewmodel.beginning.TbtiTestViewModel

@Composable
fun TbtiTestScreen(viewModel: TbtiTestViewModel = hiltViewModel()) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 51.dp, start = (23.5).dp, end = (23.5).dp, bottom = 25.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TbtiQuestionSection(
                question = viewModel.currentQuestion.value.orEmpty(),
                answers = viewModel.currentAnswers.value,
                viewModel = viewModel
            )

            Spacer(modifier = Modifier.weight(1f))

            MainButton(
                text = "다음",
                enabled = viewModel.selectedIdx.value != null,
                onClick = {
                    viewModel.selectedIdx.value?.let { index ->
                        viewModel.onAnswerSelected(index)
                        viewModel.moveToNextQuestion()
                    }
                },
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}