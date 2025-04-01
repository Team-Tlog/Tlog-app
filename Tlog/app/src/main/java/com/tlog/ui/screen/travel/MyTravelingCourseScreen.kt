package com.tlog.ui.screen.travel

import CityTravelList
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.share.BottomBar
import com.tlog.ui.component.travel.DayToggleBar
import com.tlog.viewmodel.share.CartViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import com.tlog.ui.style.Body1Bold

@Composable
fun MyTravelingCourseScreen(
    navController: NavController,
    viewModel: CartViewModel = viewModel()
) {
    val travelList by viewModel.travelList
    var selectedDay by remember { mutableStateOf(1) }
    var selectedTab by remember { mutableStateOf(0) } // course 인덱스는 1 여기는 예시라서

    val cityGrouped = travelList.groupBy { it.travelData.cityName }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp) // BottomBar 높이만큼 여백 줌
                .padding(horizontal = 12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(42.dp))
                Box(
                    modifier = Modifier
                        .width(360.dp)
                        .height(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "여행 중인 코스",
                        style = Body1Bold
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    DayToggleBar(
                        selectedDay = selectedDay,
                        onDaySelected = { selectedDay = it }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            cityGrouped.toList().forEachIndexed { cityIndex, (city, list) ->
                item {
                    CityTravelList(
                        city = city,
                        travelItems = list,
                        isLastCity = cityIndex == cityGrouped.toList().lastIndex,
                        cityIndex = cityIndex,
                        onDeleteClick = { /* 삭제 로직 */ },
                        onUpdateChecked = { i, checked ->
                            viewModel.updateChecked(i, checked)
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
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
                selectedIndex = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyTravelingCourseScreen() {
    val dummyNavController = rememberNavController()
    MyTravelingCourseScreen(navController = dummyNavController)
}