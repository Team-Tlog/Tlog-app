package com.tlog.ui.screen.travel

import CityTravelList
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import com.tlog.data.model.travel.TravelUiData
import com.tlog.ui.component.travel.DayToggleBar
import com.tlog.ui.style.Body1Bold

@Composable
fun TravelingCourse(
    travelList: List<TravelUiData>,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit,
    onUpdateChecked: (Int, Boolean) -> Unit,
    topContent: @Composable (() -> Unit)? = null
) {
    val cityGrouped = travelList.groupBy { it.travelData.cityName }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(42.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "여행 중인 코스",
                style = Body1Bold
            )
        }

        // 여기에 커스텀 topContent 삽입(ex: team member image group)
        topContent?.invoke()

        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            DayToggleBar(
                selectedDay = selectedDay,
                onDaySelected = onDaySelected
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
                .padding(horizontal = 12.dp)
        ) {
            cityGrouped.toList().forEachIndexed { cityIndex, (city, list) ->
                item {
                    CityTravelList(
                        city = city,
                        travelItems = list,
                        isLastCity = cityIndex == cityGrouped.toList().lastIndex,
                        cityIndex = cityIndex,
                        onDeleteClick = { /* 삭제 로직 */ },
                        onUpdateChecked = onUpdateChecked
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
