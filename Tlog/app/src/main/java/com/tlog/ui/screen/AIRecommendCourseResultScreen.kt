package com.tlog.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.DayToggleBar
import com.tlog.ui.component.MainButton
import com.tlog.ui.component.RetryButton
import com.tlog.ui.component.TopBar
import com.tlog.ui.component.TravelList
import com.tlog.viewmodel.CartViewModel

@Preview(showBackground = true)
@Composable
fun AIRecommendCourseResultScreen(
    viewModel: CartViewModel = viewModel()
) {
    val travelList by viewModel.travelList
    var selectedDay by remember { mutableStateOf(1) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            TopBar(text = "AI추천 코스결과")

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center)
            {
                DayToggleBar(
                    selectedDay = selectedDay,
                    onDaySelected = { selectedDay = it }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            RetryButton(
                onClick = { /* 다시 추천받기 로직 */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "부산",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            TravelList(
                travelList = travelList,
                setCheckBox = { index, checked ->
                    viewModel.updateChecked(index, checked)
                }
            )
        }

        MainButton(
            text = "저장하기",
            onClick = {

            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}
