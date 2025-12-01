package com.tlog.ui.screen.beginning

import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.tbti.TbtiQuestionSection
import com.tlog.viewmodel.beginning.TbtiTestViewModel
import com.tlog.viewmodel.base.BaseViewModel.UiEvent

@Composable
fun TbtiTestScreen(
    navController: NavController,
    viewModel: TbtiTestViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val selectedIdx by viewModel.selectedIdx.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllQuestions()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.target) {
                        if (event.clearBackStack) popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
                is UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is UiEvent.PopBackStack -> Unit
            }
        }
    }

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
                enabled = selectedIdx != null,
                onClick = {
                        viewModel.onAnswerSelected(selectedIdx!!)
                        viewModel.moveToNextQuestion()
                        viewModel.clearSelectedIdx()
                },
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}