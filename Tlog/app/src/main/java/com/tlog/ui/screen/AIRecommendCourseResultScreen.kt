package com.tlog.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.R
import com.tlog.ui.component.DayToggleBar
import com.tlog.ui.component.MainButton
import com.tlog.ui.component.RetryButton
import com.tlog.ui.component.TravelItem
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.CartViewModel

@Preview(showBackground = true)
@Composable
fun AIRecommendCourseResultScreen(
    viewModel: CartViewModel = viewModel()
) {
    val travelList by viewModel.travelList

    // 도시별로 묶기
    val cityGrouped = travelList.groupBy { it.travelData.cityName }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(117.dp))
                Text(
                    text = "AI추천 코스결과",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
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
                        selectedDay = 1,
                        onDaySelected = {}
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

            cityGrouped.forEach { (city, list) ->
                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        // 세로선
                        Column(
                            modifier = Modifier
                                .padding(start = 25.dp)
                                .width(1.dp)
                                .fillMaxHeight()
                        ) {
                            Spacer(modifier = Modifier.height(18.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                                    .background(Color.LightGray)
                            )
                        }

                        // 콘텐츠
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = 15.dp, bottom = 4.dp, top = 12.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_destination),
                                    contentDescription = "지역 아이콘",
                                    tint = MainColor,
                                    modifier = Modifier
                                        .size(width = 18.dp, height = 22.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = city,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Column(modifier = Modifier.padding(start = 37.dp)) {
                                list.forEachIndexed { index, item ->
                                    TravelItem(
                                        index = index,
                                        travelName = item.travelData.travelName,
                                        travelDescription = item.travelData.description,
                                        hashTags = item.travelData.hashTags,
                                        checked = item.checked,
                                        setCheckBox = { _, checked ->
                                            val realIndex = travelList.indexOf(item)
                                            if (realIndex != -1) {
                                                viewModel.updateChecked(realIndex, checked)
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(24.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        MainButton(
            text = "저장하기",
            onClick = { /* 저장 처리 */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}
