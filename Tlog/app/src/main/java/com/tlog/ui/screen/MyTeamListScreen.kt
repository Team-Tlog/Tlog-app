package com.tlog.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.MainButton
import com.tlog.ui.component.TeamCard
import com.tlog.ui.component.TopBar
import com.tlog.viewmodel.MyTeamListViewModel

@Preview(showBackground = true)
@Composable
fun MyTeamListScreen(
    viewModel: MyTeamListViewModel = viewModel()
) {
    val teams by viewModel.teams // State<List<TeamData>>에서 값 추출

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            text = "자신의 팀 보기"
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(teams) { team ->
                TeamCard(team = team)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        MainButton(
            text = "코드 입력해서 팀 합류",
            onClick = { /* 코드 입력 화면 이동 */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        MainButton(
            text = "팀 생성",
            onClick = { /* 팀 생성 화면 이동 */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
    }
}
