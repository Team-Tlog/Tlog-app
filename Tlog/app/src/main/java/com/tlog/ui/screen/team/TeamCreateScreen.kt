package com.tlog.ui.screen.team

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.share.TitleInputField
import com.tlog.ui.component.share.TopBar
import com.tlog.viewmodel.base.BaseViewModel.UiEvent
import com.tlog.viewmodel.team.TeamNameViewModel


@Composable
fun TeamCreateScreen(
    viewModel: TeamNameViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.target) {
                        if (event.clearBackStack) popUpTo(navController.graph.id) { inclusive = true }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is UiEvent.PopBackStack -> Unit
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding()           // 키보드가 딸려 올라오도록
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        TopBar(
            text = "팀 생성"
        )

        Spacer(modifier = Modifier.height(35.dp))


        TitleInputField(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = "팀 이름을 정해주세요!",
            value = viewModel.teamName.value,
            onValueChange = {
                viewModel.updateTeamName(it)
            },
            placeholderText = "입력해주세요",
        )


        Spacer(modifier = Modifier.weight(1f))

        MainButton(
            text = "팀 생성하기",
            onClick = {
                viewModel.navToTeamInfoInput(viewModel.teamName.value)
            },
            enabled = viewModel.checkTeamName(),
            modifier = Modifier
                .height(70.dp)
                .padding(bottom = 15.dp, start = 24.dp, end = 24.dp)
        )
    }
}