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
    var selectedDay by remember { mutableStateOf(1) }

    // 도시별로 묶기
    val cityGrouped = travelList.groupBy { it.travelData.cityName }

    Box(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.systemBars)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
                //.padding(bottom = 80.dp),
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

            cityGrouped.toList().forEachIndexed{ cityIndex, (city, list) ->
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
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

                        Row(modifier = Modifier.padding(start = 24.dp)) {
                            val totalHeight = list.size * 125

                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(totalHeight.dp)
                                    .background(Color.LightGray)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                list.forEachIndexed { index, item ->
                                    Box(modifier = Modifier.fillMaxWidth()) {
                                        TravelItem(
                                            index = index,
                                            travelName = item.travelData.travelName,
                                            travelDescription = item.travelData.description,
                                            hashTags = item.travelData.hashTags,
                                            checked = item.checked,
                                            setCheckBox = { i, checked ->
                                                viewModel.updateChecked(i, checked)
                                            },
                                            showCheckbox = false // 체크박스 숨김
                                        )
                                        IconButton(
                                            onClick = {
                                                /* 여행지 삭제*/
                                            },
                                            modifier = Modifier
                                                .align(Alignment.CenterEnd)
                                                .padding(end = 20.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_delete),
                                                contentDescription = "삭제 아이콘",
                                                tint = Color.Unspecified
                                            )
                                        }

                                        if (cityIndex == cityGrouped.toList().lastIndex && index == list.lastIndex)
                                            Spacer(modifier = Modifier.height(145.dp))

                                    }
                                    Spacer(modifier = Modifier.height(24.dp))
                                }
                            }
                        }
                    }
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