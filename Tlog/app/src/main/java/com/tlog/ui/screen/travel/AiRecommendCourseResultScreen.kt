package com.tlog.ui.screen.travel

import CityTravelList
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.ui.component.travel.DayToggleBar
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.style.BodyTitle
import com.tlog.viewmodel.base.BaseViewModel.UiEvent
import com.tlog.viewmodel.travel.AiRecommendCourseResultViewModel
import com.tlog.viewmodel.travel.CourseSharedViewModel

@Composable
fun AiRecommendCourseResultScreen(
    viewModel: AiRecommendCourseResultViewModel = hiltViewModel(),
    sharedViewModel: CourseSharedViewModel,
    isTeam: Boolean,
    navController: NavController
) {
    val context = LocalContext.current
    val selectedDay by viewModel.selectedDay.collectAsState()
    val uiTravels by viewModel.uiTravels.collectAsState()
    val cityGrouped = uiTravels.groupBy { it.city }
    val teamId by sharedViewModel.teamId.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.setAiCourses(
            courses = sharedViewModel.aiCourses.value,
            dayOfCount = sharedViewModel.getDayOfCount(),
            startDate = sharedViewModel.getStartDate(),
            endDate = sharedViewModel.getEndDate()
        )

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

    Box(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(103.dp))
                Text(
                    text = "AI추천 코스결과",
                    style = BodyTitle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(41.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    DayToggleBar(
                        size = viewModel.getDayCount(),
                        selectedDay = selectedDay,
                        onDaySelected = { viewModel.updateSelectedDay(it) }
                    )
                }

//                Spacer(modifier = Modifier.height(20.dp))
//
//                RetryButton(
//                    onClick = { /* 다시 추천 로직 */ },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentWidth(Alignment.CenterHorizontally)
//                )

                Spacer(modifier = Modifier.height(41.dp))
            }

            cityGrouped.toList().forEachIndexed { cityIndex, (city, list) ->
                item {
                    CityTravelList(
                        city = city,
                        travelItems = list,
                        onDeleteClick = { viewModel.deleteTravelByName(it) }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(145.dp))
            }
        }


        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 15.dp)
        ) {
            MainButton(
                text = "저장하기",
                onClick = { viewModel.saveCourse(isTeam = isTeam, teamId = teamId) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )
        }
    }
}
