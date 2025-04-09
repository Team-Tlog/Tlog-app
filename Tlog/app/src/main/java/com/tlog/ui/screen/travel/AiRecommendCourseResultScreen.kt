package com.tlog.ui.screen.travel

import CityTravelList
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.travel.DayToggleBar
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.travel.RetryButton
import com.tlog.ui.style.BodyTitle
import com.tlog.viewmodel.share.CartViewModel

@Preview(showBackground = true)
@Composable
fun AiRecommendCourseResultScreen(
    viewModel: CartViewModel = viewModel()
) {
    val travelList by viewModel.travelList
    var selectedDay by remember { mutableStateOf(1) }

    // 도시별로 묶기
    val cityGrouped = travelList.groupBy { it.travelData.cityName }

    Box(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.systemBars)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(117.dp))
                Text(
                    text = "AI추천 코스결과",
                    style = BodyTitle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(75.dp))

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

                RetryButton(
                    onClick = { /* 다시 추천 로직 */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            cityGrouped.toList().forEachIndexed { cityIndex, (city, list) ->
                item {
                    CityTravelList(
                        city = city,
                        travelItems = list,
                        isLastCity = cityIndex == cityGrouped.toList().lastIndex,
                        cityIndex = cityIndex,
                        onDeleteClick = { travelItem ->
                            // 여행지 삭제 로직
                        },
                        onUpdateChecked = { i, checked ->
                            viewModel.updateChecked(i, checked)
                        }
                    )
                }
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
                onClick = { /* 저장 로직 처리*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )
        }

    }
}