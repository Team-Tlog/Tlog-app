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
import com.tlog.ui.component.travel.DayToggleBar
import com.tlog.ui.component.travel.TravelingCourse
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
        Box(
            modifier = Modifier
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

                    Spacer(modifier = Modifier.height(41.dp))
                }

                cityGrouped.toList().forEachIndexed { cityIndex, (city, list) ->
                    item {
                        CityTravelList(
                            city = city,
                            travelItems = list,
                            onDeleteClick = {

                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(145.dp))
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
}
