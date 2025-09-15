package com.tlog.ui.screen.team

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
import com.tlog.viewmodel.base.BaseViewModel.UiEvent
import com.tlog.viewmodel.team.MyTeamListViewModel

@Composable
fun MyTeamListScreen(
    viewModel: MyTeamListViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchTeamsFromServer()

        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.target) {
                        if (event.clearBackStack) popUpTo(navController.graph.id) { inclusive = true }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
                is UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is UiEvent.PopBackStack -> Unit
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
                    onClick = { teamId ->
                        viewModel.navToTeamDetail(teamId)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        MainButton(
            text = "코드 입력해서 팀 합류",
            onClick = {
                viewModel.navToJoinTeam()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        MainButton(
            text = "팀 생성",
            onClick = {
                viewModel.navToCreateTeam()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
    }
}
