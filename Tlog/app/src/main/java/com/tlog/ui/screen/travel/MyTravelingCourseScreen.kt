package com.tlog.ui.screen.travel

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.share.BottomBar
import com.tlog.viewmodel.tmp.TmpCartViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment

@Composable
fun MyTravelingCourseScreen(
    navController: NavController,
    viewModel: TmpCartViewModel = viewModel()
) {
    val travelList by viewModel.cartList
    var selectedDay by remember { mutableStateOf(2) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        TravelingCourse(
            travelList = travelList,
            selectedDay = selectedDay,
            onDaySelected = { selectedDay = it },
            onUpdateChecked = { i, checked ->
                viewModel.updateChecked(i, checked)
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

// 하단바 미리보기용 추후 삭제 예정
@Preview(showBackground = true)
@Composable
fun PreviewMyTravelingCourseScreen() {
    val dummyNavController = rememberNavController()
    MyTravelingCourseScreen(navController = dummyNavController)
}
