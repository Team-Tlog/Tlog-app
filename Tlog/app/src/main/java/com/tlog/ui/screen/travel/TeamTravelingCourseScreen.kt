package com.tlog.ui.screen.travel

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.share.BottomBar
import com.tlog.viewmodel.tmp.TmpCartViewModel
import com.tlog.viewmodel.team.TeamDetailViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import com.tlog.ui.component.travel.TravelingCourse

@Composable
fun TeamTravelingCourseScreen(
    navController: NavController,
    viewModel: TmpCartViewModel = viewModel(),
    teamViewModel: TeamDetailViewModel = viewModel()
) {
    val travelList by viewModel.travelList
    var selectedDay by remember { mutableStateOf(1) }
    var selectedTab by remember { mutableStateOf(1) }

    // api 적용 할 때 타입 변경으로 주석 처리 (추후 처리할 것)
//    val teamMembers = teamViewModel.teamData.members
//    val memberImageUrls = teamMembers.map { it.imageUrl }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        TravelingCourse(
            travelList = travelList,
            selectedDay = selectedDay,
            onDaySelected = { selectedDay = it },
            onUpdateChecked = { i, checked ->
                viewModel.updateChecked(i, checked)
            },
            topContent = {
                Spacer(modifier = Modifier.height(13.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    //TeamMemberImageGroup(memberImageUrls = memberImageUrls)
                }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter)
        ) {
            BottomBar(
                navController = navController,
                selectedIndex = selectedTab
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTeamTravelingCourseScreen() {
    val dummyNavController = rememberNavController()
    TeamTravelingCourseScreen(navController = dummyNavController)
}