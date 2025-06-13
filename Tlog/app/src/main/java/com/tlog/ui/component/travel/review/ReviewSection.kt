package com.tlog.ui.component.travel.review

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.data.model.travel.DetailReview
import com.tlog.data.model.travel.Review
import com.tlog.ui.theme.MainFont


@Composable
fun ReviewSection(
    avgStarRating: Double,
    ratingDistribution: Map<String, Int>,
    reviewList: List<DetailReview>,
    reviewCnt: Int = Int.MAX_VALUE,
    moreReview: () -> Unit,
    reviewWrite: () -> Unit,
    onClick: (String) -> Unit
) {
    Column {
        Column(
            modifier = Modifier
                .padding(horizontal = (31.5).dp)
        ) {
            // 후기 리뷰작성 아이콘
            ReviewHeader(
                reviewWrite = reviewWrite
            )

            Spacer(modifier = Modifier.height(29.dp))

            // 평균 별점 + 별점 + 리뷰개수
            ReviewStatistics(
                avgStarRating = avgStarRating,
                ratingDistribution = ratingDistribution
            )
        }

        Spacer(modifier = Modifier.height(29.dp))


        // 더보기
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        moreReview()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "더보기",
                    fontFamily = MainFont,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF3C6AFF)
                )

                Icon(
                    painter = painterResource(R.drawable.ic_right_arrow),
                    contentDescription = "리뷰 더보기",
                    tint = Color.Unspecified
                )
            }
        }

        Spacer(modifier = Modifier.height(29.dp))

        // 리뷰 2개 띄우기
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = (31.5).dp)
        ) {
            ReviewList(
                reviewList = reviewList,
                maxCnt = reviewCnt,
                onClick = onClick
            )
        }
    }
}