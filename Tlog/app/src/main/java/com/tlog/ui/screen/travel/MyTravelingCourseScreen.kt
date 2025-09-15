package com.tlog.ui.screen.travel

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tlog.ui.component.share.BottomBar
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import com.tlog.ui.component.travel.TravelingCourse

@Composable
fun MyTravelingCourseScreen(
    navController: NavController,
//    viewModel: TmpCartViewModel = hiltViewModel()
) {
//    val travelList by viewModel.travelList
//    val checkedList by viewModel.checkedList
    var selectedDay by remember { mutableStateOf(2) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        TravelingCourse(
            travelList = emptyList(),
            selectedDay = selectedDay,
            onDaySelected = { selectedDay = it },
            onUpdateChecked = { i, checked ->
//                viewModel.updateChecked(i, checked)
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
                selectedIndex = 1
            )
        }
    }
}
