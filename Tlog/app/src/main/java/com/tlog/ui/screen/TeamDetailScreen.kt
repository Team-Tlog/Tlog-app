package com.tlog.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.R
import com.tlog.ui.component.MainButton
import com.tlog.ui.component.TravelList
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.CartViewModel
import com.tlog.viewmodel.TeamDetailViewModel
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tlog.ui.component.DesignOne
import com.tlog.ui.component.DesignThree
import com.tlog.ui.component.DesignTwo
import com.tlog.ui.theme.MainFont


enum class PageState { ONE, TWO, THREE }

@Preview(showBackground = true)
@Composable
fun TeamDetailScreen(
    cartViewModel: CartViewModel = viewModel(),
    teamDetailViewModel: TeamDetailViewModel = viewModel()
) {
    var currentState by remember { mutableStateOf(PageState.TWO) }
    var dragOffset by remember { mutableStateOf(0f) }
    val dragThreshold = 100f


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box( // 상단바 색칠
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.systemBars.asPaddingValues().calculateTopPadding())
                .background(MainColor)
                .align(Alignment.TopCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.systemBars)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                currentState = when {
                                    dragOffset < -dragThreshold -> {
                                        // 위로 드래그
                                        when (currentState) {
                                            PageState.TWO -> PageState.ONE
                                            PageState.THREE -> PageState.TWO
                                            else -> currentState
                                        }
                                    }
                                    dragOffset > dragThreshold -> {
                                        // 아래로 드래그
                                        when (currentState) {
                                            PageState.ONE -> PageState.TWO
                                            PageState.TWO -> PageState.THREE
                                            else -> currentState
                                        }
                                    }
                                    else -> currentState // 변화 없음
                                }
                                dragOffset = 0f
                            },
                            onDrag = { change, dragAmount ->
                                dragOffset += dragAmount.y
                                change.consume()
                            }
                        )
                    }
            ) {
                when (currentState) {
                    PageState.ONE -> DesignOne(teamData = teamDetailViewModel.teamData)
                    PageState.TWO -> DesignTwo(teamData = teamDetailViewModel.teamData)
                    PageState.THREE -> DesignThree(teamData = teamDetailViewModel.teamData)
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            TravelList(
                travelList = cartViewModel.travelList.value,
                setCheckBox = { index, checked ->
                     cartViewModel.updateChecked(index, checked)
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 15.dp)
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.systemBars)
        ) {
            MainButton(
                text = "AI 코스 탐색 시작",
                onClick = {
                    Log.d("AI Start", "my click!!")
                },
                modifier = Modifier
                    .height(55.dp)
            )
        }
    }
}

