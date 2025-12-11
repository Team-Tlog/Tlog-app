package com.tlog.ui.component.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.tlog.domain.model.travel.review.Review


@Composable
fun ReviewGroup(
    reviewList: List<Review>,
    maxCnt: Int = Int.MAX_VALUE
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(29.dp)
    ) {
        for (i in reviewList.indices) {
            if (maxCnt < i + 1)
                break

            val review = reviewList[i]

            ReviewItem(
                review = review
            )
        }
    }


}