package com.tlog.ui.screen.team

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.team.TeamCard
import com.tlog.ui.component.share.TopBar
import com.tlog.viewmodel.team.MyTeamListViewModel.UiEvent
import com.tlog.viewmodel.team.MyTeamListViewModel

@Composable
fun MyTeamListScreen(
    viewModel: MyTeamListViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchTeamsFromServer()

        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ApiSuccess -> {
                    Log.d("MyTeamListScreen", "ApiSuccess")
                }
                is UiEvent.ApiError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


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
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.teamsList.value) { team ->
                TeamCard(
                    team = team,
                    onDeleteClick = { viewModel.deleteTeam(it) },
                    onClick = { teamId -> navController.navigate("teamDetail/${teamId}") }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        MainButton(
            text = "코드 입력해서 팀 합류",
            onClick = {
                navController.navigate("joinTeam")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        MainButton(
            text = "팀 생성",
            onClick = {
                navController.navigate("createTeam")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
    }
}
