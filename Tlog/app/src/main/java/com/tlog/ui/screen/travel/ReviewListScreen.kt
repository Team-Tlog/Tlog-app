package com.tlog.ui.screen.travel

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.share.DropDown
import com.tlog.ui.component.travel.TravelInfoTopBar
import com.tlog.ui.component.travel.review.ReviewList
import com.tlog.ui.component.travel.review.ReviewStatistics
import com.tlog.viewmodel.travel.TravelInfoViewModel


@Preview
@Composable
fun ReviewListScreen(viewModel: TravelInfoViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(Color.White)
    ) {
        Column {
            TravelInfoTopBar()

            Spacer(modifier = Modifier.height(29.dp))

            Column(
                modifier = Modifier
                    .padding((31.5).dp)
            ) {
                ReviewStatistics(
                    avgStarRating = viewModel.selectedTravelInfo.value.avgStarRating,
                    starRatings = viewModel.selectedTravelInfo.value.starRatings
                )

                Spacer(modifier = Modifier.height(29.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    DropDown(
                        options = listOf("추천순", "낮은순", "높은순"),
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
                    reviewList = viewModel.selectedTravelInfo.value.reviewList,
                )
            }
        }
    }
}