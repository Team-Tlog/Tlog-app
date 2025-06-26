package com.tlog.ui.screen.team

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.team.TeamDetailViewModel
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.tlog.data.model.share.Location
import com.tlog.data.model.travel.Travel
import com.tlog.ui.component.team.SmallDesign
import com.tlog.ui.component.team.BigDesign
import com.tlog.ui.component.team.DefaultDesign
import com.tlog.ui.component.travel.TravelList


enum class PageState { DEFAULT, SMALL, BIG }

@Composable
fun TeamDetailScreen(
    viewModel: TeamDetailViewModel = hiltViewModel(),
    teamId: String,
) {
    var sizeState by remember { mutableStateOf(PageState.DEFAULT) }
    val listState = rememberLazyListState()
    var showPopup by remember { mutableStateOf(false) }
    val teamDataState = viewModel.teamData.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.getTeamDetail(teamId)
    }

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

        teamDataState.value?.let { teamData ->
            Column {
                Column(
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.systemBars)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    when (sizeState) {
                                        PageState.BIG -> sizeState = if (listState.firstVisibleItemIndex != 0) PageState.SMALL else PageState.DEFAULT
                                        PageState.DEFAULT -> sizeState = PageState.BIG //if (listState.firstVisibleItemIndex == 0) PageState.BIG else sizeState
                                        PageState.SMALL -> sizeState =PageState.SMALL
                                    }
                                }
                        ) {
                            when (sizeState) {
                                PageState.SMALL -> SmallDesign(
                                    teamData = teamData,
                                    showPopup = showPopup,
                                    addMemberClick = { showPopup = true },
                                    onDismiss = { showPopup = false }
                                )

                                PageState.DEFAULT -> DefaultDesign(
                                    teamData = teamData,
                                    showPopup = showPopup,
                                    addMemberClick = { showPopup = true },
                                    onDismiss = { showPopup = false }
                                )

                                PageState.BIG -> BigDesign(
                                    teamData = teamData,
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    TravelList(
                        travelList = //teamData.wishlist,
                            listOf(
                                Travel(
                                name = "테스트1",
                                address = "테스트",
                                location = Location("0.0", "0.0"),
                                city = "서울",
                                district = "강남구",
                                hasParking = true,
                                petFriendly = true,
                                imageUrl = "",
                                description = "설명 설명 설명 설명",
                                customTags = listOf("테스트", "안녕")
                                ),
                                Travel(
                                    name = "테스트2",
                                    address = "테스트",
                                    location = Location("0.0", "0.0"),
                                    city = "서울",
                                    district = "강남구",
                                    hasParking = true,
                                    petFriendly = true,
                                    imageUrl = "",
                                    description = "설명 설명 설명 설명",
                                    customTags = listOf("테스트", "안녕")
                                ),
                                Travel(
                                    name = "테스트3",
                                    address = "테스트",
                                    location = Location("0.0", "0.0"),
                                    city = "서울",
                                    district = "강남구",
                                    hasParking = true,
                                    petFriendly = true,
                                    imageUrl = "",
                                    description = "설명 설명 설명 설명",
                                    customTags = listOf("테스트", "안녕")
                                ),
                                Travel(
                                    name = "테스트4",
                                    address = "테스트",
                                    location = Location("0.0", "0.0"),
                                    city = "서울",
                                    district = "강남구",
                                    hasParking = true,
                                    petFriendly = true,
                                    imageUrl = "",
                                    description = "설명 설명 설명 설명",
                                    customTags = listOf("테스트", "안녕")
                                ),
                                Travel(
                                    name = "테스트5",
                                    address = "테스트",
                                    location = Location("0.0", "0.0"),
                                    city = "서울",
                                    district = "강남구",
                                    hasParking = true,
                                    petFriendly = true,
                                    imageUrl = "",
                                    description = "설명 설명 설명 설명",
                                    customTags = listOf("테스트", "안녕")
                                ),
                                Travel(
                                    name = "테스트6",
                                    address = "테스트",
                                    location = Location("0.0", "0.0"),
                                    city = "서울",
                                    district = "강남구",
                                    hasParking = true,
                                    petFriendly = true,
                                    imageUrl = "",
                                    description = "설명 설명 설명 설명",
                                    customTags = listOf("테스트", "안녕")
                                ),
                                Travel(
                                    name = "테스트7",
                                    address = "테스트",
                                    location = Location("0.0", "0.0"),
                                    city = "서울",
                                    district = "강남구",
                                    hasParking = true,
                                    petFriendly = true,
                                    imageUrl = "",
                                    description = "설명 설명 설명 설명",
                                    customTags = listOf("테스트", "안녕")
                                ),
                                Travel(
                                    name = "테스트8",
                                    address = "테스트",
                                    location = Location("0.0", "0.0"),
                                    city = "서울",
                                    district = "강남구",
                                    hasParking = true,
                                    petFriendly = true,
                                    imageUrl = "",
                                    description = "설명 설명 설명 설명",
                                    customTags = listOf("테스트", "안녕")
                                ),
                                Travel(
                                    name = "테스트9",
                                    address = "테스트",
                                    location = Location("0.0", "0.0"),
                                    city = "서울",
                                    district = "강남구",
                                    hasParking = true,
                                    petFriendly = true,
                                    imageUrl = "",
                                    description = "설명 설명 설명 설명",
                                    customTags = listOf("테스트", "안녕")
                                ),
                                Travel(
                                    name = "테스트10",
                                    address = "테스트",
                                    location = Location("0.0", "0.0"),
                                    city = "서울",
                                    district = "강남구",
                                    hasParking = true,
                                    petFriendly = true,
                                    imageUrl = "",
                                    description = "설명 설명 설명 설명",
                                    customTags = listOf("테스트", "안녕")
                                ),
                                Travel(
                                    name = "테스트11",
                                    address = "테스트",
                                    location = Location("0.0", "0.0"),
                                    city = "서울",
                                    district = "강남구",
                                    hasParking = true,
                                    petFriendly = true,
                                    imageUrl = "",
                                    description = "설명 설명 설명 설명",
                                    customTags = listOf("테스트", "안녕")
                                )
                        ),
                        listState = listState,
                        onClick = { travelName ->
                            viewModel.updateCheckList(travelName)
                        },
                        isChecked = { travelName ->
                            viewModel.isChecked(travelName)
                        }
                    )
                }
            }
        } ?: {

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

