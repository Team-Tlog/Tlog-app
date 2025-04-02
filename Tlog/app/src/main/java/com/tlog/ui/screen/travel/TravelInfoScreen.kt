package com.tlog.ui.screen.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.R
import com.tlog.ui.component.travel.ReviewSection
import com.tlog.ui.component.travel.SimilarTravelSection
import com.tlog.ui.component.travel.TravelInfoSummary
import com.tlog.ui.component.travel.TravelTopImageBox
import com.tlog.viewmodel.travel.TravelInfoViewModel


@Preview
@Composable
fun TravelInfoScreen(viewModel: TravelInfoViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Column {
            TravelTopImageBox(imageUrl = R.drawable.tmp_jeju)

            Box( // 박스의 범위도 고민되고 이 방식이 맞나도 고민됨 일단 여기선 여행지 이름, 위치, 별점, 해시태그까지 박스
                modifier = Modifier
                    .offset(y = (-79).dp)
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                    )
                    .padding(top = 30.dp)
            ) {
                // 여행지 정보 ~ 회색 선
                Column {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = (31.5).dp)
                    ) {

                        TravelInfoSummary(viewModel.selectedTravelInfo.value)

                        Spacer(modifier = Modifier.height(89.dp))

                        Divider(
                            color = Color(0xFFE3E3E3),
                            thickness = 1.dp
                        )
                    }

                    Spacer(modifier = Modifier.height(29.dp))

                    // 리뷰 헤더 + 리뷰 부분
                    ReviewSection(
                        avgStarRating = viewModel.selectedTravelInfo.value.avgStarRating,
                        starRatings = viewModel.selectedTravelInfo.value.starRatings,
                        reviewList = viewModel.selectedTravelInfo.value.reviewList,
                        reviewCnt = 2
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    // 비슷한 여행지 부분
                    SimilarTravelSection()
                }
            }
        }


    }
}
