package com.tlog.ui.screen.travel

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.travel.CheckCartList
import com.tlog.ui.style.Body2Regular
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.base.BaseViewModel.UiEvent
import com.tlog.viewmodel.travel.AiCourseSelectCartViewModel
import com.tlog.viewmodel.travel.CourseSharedViewModel


@Composable
fun AiCourseSelectCartScreen(
    viewModel: AiCourseSelectCartViewModel = hiltViewModel(),
    navController: NavController,
    sharedViewModel: CourseSharedViewModel,
) {
    val context = LocalContext.current
    val selectedNames by sharedViewModel.selectedTravelNames.collectAsState()

    LaunchedEffect(selectedNames) {
        viewModel.setCheckedList(selectedNames)
    }


    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.target) {
                        if (event.clearBackStack) popUpTo(navController.graph.id) { inclusive = true }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
                is UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is UiEvent.PopBackStack -> Unit
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.aiTravelMap.collect { map ->
            if (map.isNotEmpty()) {
                sharedViewModel.setAiTravelMap(map)
                viewModel.navToAiCourseResult()
                Log.d("AiCourse", "Data transferred to shared: $map")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(37.dp))

            Text(
                text = "가고싶은 여행지를\n선택해주세요!",
                style = BodyTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(46.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 22.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "전체 선택",
                    modifier = Modifier.clickable {
                        viewModel.allChecked()
                        Log.d("all select", "my click!!")
                    },
                    style = Body2Regular,
                    color = MainColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            CheckCartList(
                travelList = viewModel.cartList.value,
                onClick = { viewModel.updateCheckedTravelList(it) },
                isChecked = { viewModel.isChecked(it) },
            )
        }

        MainButton(
            text = "AI 코스 추천받기",
            onClick = {
                viewModel.getAiCourse(sharedViewModel.aiRequest.value!!)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 15.dp)
                .align(Alignment.BottomCenter)
        )
    }
}