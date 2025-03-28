package com.tlog.ui.screen

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.MainButton
import com.tlog.ui.component.TravelList
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.CartViewModel
import com.tlog.viewmodel.TeamDetailViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.pointer.pointerInput
import com.tlog.ui.component.team.SmallDesign
import com.tlog.ui.component.team.BigDesign
import com.tlog.ui.component.team.DefaultDesign


enum class PageState { SMALL, DEFAULT, BIG }

@Preview(showBackground = true)
@Composable
fun TeamDetailScreen(
    cartViewModel: CartViewModel = viewModel(),
    teamDetailViewModel: TeamDetailViewModel = viewModel()
) {
    var sizeState by remember { mutableStateOf(PageState.DEFAULT) }
    var dragOffset by remember { mutableStateOf(0f) }
    val dragThreshold = 100f // 드래그 민감도

    val height by animateDpAsState(
        targetValue = when (sizeState) {
            PageState.SMALL -> 183.dp
            PageState.DEFAULT -> 288.dp
            PageState.BIG -> 368.dp
        },
        animationSpec = tween(
            durationMillis = 500, // 애니매이션 속도
            easing = LinearOutSlowInEasing // 애니매이션 가속도
        ),
        label = "AnimatedHeaderHeight"
    )

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

        Column {
            Column(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragEnd = {
                                        sizeState = when {
                                            dragOffset < -dragThreshold -> {
                                                // 위로 드래그
                                                when (sizeState) {
                                                    PageState.DEFAULT -> PageState.SMALL
                                                    PageState.BIG -> PageState.DEFAULT
                                                    else -> sizeState
                                                }
                                            }

                                            dragOffset > dragThreshold -> {
                                                // 아래로 드래그
                                                when (sizeState) {
                                                    PageState.SMALL -> PageState.DEFAULT
                                                    PageState.DEFAULT -> PageState.BIG
                                                    else -> sizeState
                                                }
                                            }

                                            else -> sizeState // 그대로 유지
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
                        when (sizeState) {
                            PageState.SMALL -> SmallDesign(teamData = teamDetailViewModel.teamData)
                            PageState.DEFAULT -> DefaultDesign(teamData = teamDetailViewModel.teamData)
                            PageState.BIG -> BigDesign(teamData = teamDetailViewModel.teamData)
                        }
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
        }

        Box(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 15.dp)
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.navigationBars)
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

