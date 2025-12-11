package com.tlog.ui.screen.travel

import CityTravelList
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tlog.ui.component.share.BottomBar
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.tlog.ui.component.share.NotFound
import com.tlog.ui.component.travel.DayToggleBar
import com.tlog.ui.style.BodyTitle
import com.tlog.viewmodel.travel.MyTravelingCourseViewModel

@Composable
fun MyTravelingCourseScreen(
    viewModel: MyTravelingCourseViewModel = hiltViewModel(),
    navController: NavController
) {
    val selectedDay by viewModel.selectedDay.collectAsState()
    val uiTravels by viewModel.uiTravels.collectAsState()
    val cityGrouped = uiTravels.groupBy { it.city }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(103.dp))
            Text(
                text = "여행중인 코스",
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

            Spacer(modifier = Modifier.height(41.dp))

            if (!cityGrouped.isEmpty()) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 145.dp)
                ) {
                    cityGrouped.toList().forEach { (city, list) ->
                        item {
                            CityTravelList(
                                city = city,
                                travelItems = list,
                                showDeleteIcon = false
                            )
                        }
                    }
                }
            }
        }

        if (cityGrouped.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                NotFound(text = "여행중인 코스가 없습니다.")
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter)
        ) {
            BottomBar(
                navController = navController,
                selectedIndex = 1
            )
        }
    }
}
