package com.tlog.ui.component.travel.review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tlog.data.model.travel.Review

// 250603 정찬 -> 안쓰는듯? 추후 안쓰면 제거하자~
@Composable
fun ReviewListSection(
    avgStarRating: Double,
    starRatings: List<Int>,
    reviewList: List<Review>,
    reviewCnt: Int = Int.MAX_VALUE
) {
    Column {
        // 후기 리뷰작성 아이콘
        //ReviewHeader()

        Spacer(modifier = Modifier.height(29.dp))

        // 평균 별점 + 별점 + 리뷰개수
        /*ReviewStatistics(
            avgStarRating = avgStarRating,
            starRatings = starRatings
        )*/


        Spacer(modifier = Modifier.height(29.dp))


//        ReviewList(
//            reviewList = reviewList,
//            maxCnt = reviewCnt
//        )
    }
}