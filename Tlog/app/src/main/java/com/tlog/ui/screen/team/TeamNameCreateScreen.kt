package com.tlog.ui.screen.team

import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.team.TeamNameInputField
import com.tlog.ui.component.share.TopBar
import com.tlog.viewmodel.team.TeamNameViewModel


@Composable
fun TeamNameCreateScreen(
    userId: String,
    viewModel: TeamNameViewModel = viewModel()
) {

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

        TeamNameInputField(
            text = "팀 이름을 정해주세요!",
            value = viewModel.TeamName.value,
            onValueChange = {
                viewModel.updateTeamName(it)
                Log.d("TeamNameValue", viewModel.TeamName.value)
            },
            placeholderText = "입력해주세요",

        )


        Spacer(modifier = Modifier.weight(1f))

        // 입력값이 있을 때만 버튼 표시
        if (viewModel.TeamName.value.isNotEmpty()) {
            MainButton(
                text = "팀 생성하기",
                onClick = {
                    viewModel.createTeam(userId)
                },
                modifier = Modifier
                    .height(70.dp)
                    .padding(start = 10.dp, end = 10.dp, bottom = 15.dp)
            )
        }
    }
}