package com.tlog.ui.screen.team

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.travel.TravelList
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.share.CartViewModel
import com.tlog.viewmodel.team.TeamDetailViewModel
import androidx.compose.ui.Alignment
import com.tlog.ui.component.team.SmallDesign
import com.tlog.ui.component.team.BigDesign
import com.tlog.ui.component.team.DefaultDesign
import com.tlog.ui.component.team.MidiumDesign


enum class PageState { DEFAULT, SMALL, MEDIUM, BIG }

@Preview(showBackground = true)
@Composable
fun TeamDetailScreen(
    cartViewModel: CartViewModel = viewModel(),
    teamDetailViewModel: TeamDetailViewModel = viewModel()
) {
    var sizeState by remember { mutableStateOf(PageState.DEFAULT) }

    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow {
            listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset
        }
            .collect { (index, offset) ->
                sizeState = when {
                    index == 0 && offset == 0 -> PageState.DEFAULT
                    index != 0 -> PageState.SMALL
                    else -> sizeState
                }
            }
    }

    val height by animateDpAsState(
        targetValue = when (sizeState) {
            PageState.SMALL -> 183.dp
            PageState.DEFAULT -> 288.dp
            PageState.MEDIUM -> 236.dp
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
                            .clickable {
                                when(sizeState) {
                                    PageState.BIG -> sizeState = if (listState.firstVisibleItemIndex != 0) PageState.SMALL else PageState.DEFAULT
                                    PageState.DEFAULT -> sizeState =
                                        PageState.BIG //if (listState.firstVisibleItemIndex == 0) PageState.BIG else sizeState
                                    PageState.SMALL -> sizeState =
                                        PageState.MEDIUM //if (listState.firstVisibleItemIndex == 0) PageState.BIG else sizeState
                                    PageState.MEDIUM -> sizeState = PageState.SMALL
                                }
                            }
                    ) {
                        when (sizeState) {
                            PageState.SMALL -> SmallDesign(teamData = teamDetailViewModel.teamData)
                            PageState.DEFAULT -> DefaultDesign(teamData = teamDetailViewModel.teamData)
                            PageState.BIG -> BigDesign(teamData = teamDetailViewModel.teamData)
                            PageState.MEDIUM -> MidiumDesign(teamData = teamDetailViewModel.teamData)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                TravelList(
                    travelList = cartViewModel.travelList.value,
                    setCheckBox = { index, checked ->
                        cartViewModel.updateChecked(index, checked)
                    },
                    listState = listState
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

