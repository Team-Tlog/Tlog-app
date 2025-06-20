package com.tlog.ui.screen.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.ui.component.share.DropDown
import com.tlog.ui.component.travel.TravelInfoTopBar
import com.tlog.ui.component.travel.review.ReviewHeader
import com.tlog.ui.component.travel.review.ReviewList
import com.tlog.ui.component.travel.review.ReviewStatistics
import com.tlog.viewmodel.review.ReviewListViewModel


@Composable
fun ReviewListScreen(
    viewModel: ReviewListViewModel = hiltViewModel(),
    travelId: String,
    travelName: String,
    navController: NavController
) {
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.getReviewList(id = travelId)
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItemCount = listState.layoutInfo.totalItemsCount
            lastVisibleItem?.index == totalItemCount - 2 // 마지막에서 2번째 친구면
        }.collect { isLastItemVisible ->
            if (isLastItemVisible) {
                viewModel.getNextPage(travelId)
            }
        }
    }
    LaunchedEffect(viewModel.sortOption.value) {
        viewModel.getReviewList(id = travelId)
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = listState
        ) {
            item {
                TravelInfoTopBar(
                    isScrap = viewModel.isScraped(travelId),
                    clickScrap = { viewModel.toggleScrap(travelId) }
                )

                Spacer(modifier = Modifier.height(23.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = (31.5).dp)
                ) {
                    ReviewHeader(
                        reviewWrite = { navController.navigate("review/$travelId/$travelName") }
                    )
                }

                Spacer(modifier = Modifier.height(29.dp))

                Column(
                    modifier = Modifier
                        .padding(31.5.dp)
                ) {
                    ReviewStatistics(
                        avgStarRating = viewModel.rating.value,
                        ratingDistribution = viewModel.ratingDistribution.value
                    )

                    Spacer(modifier = Modifier.height(29.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        DropDown(
                            options = listOf("날짜순", "높은순", "낮은순") - viewModel.sortOption.value,
                            value = viewModel.sortOption.value,
                            valueChange = {
                                viewModel.updateSelectOption(it)
                            },
                            modifier = Modifier
                                .heightIn(max = 130.dp)
                                .width(98.dp)
                                .align(Alignment.CenterEnd)
                        )
                    }

                    Spacer(modifier = Modifier.height(29.dp))

                    ReviewList(
                        reviewList = viewModel.reviewList.value,
                        onClick = { userId ->
                            navController.navigate("snsMyPage/$userId")
                        }
                    )
                }
            }
        }
    }

}