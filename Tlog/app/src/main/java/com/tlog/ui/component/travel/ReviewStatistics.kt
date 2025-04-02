package com.tlog.ui.component.travel

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.style.Title
import com.tlog.ui.theme.MainFont


@Composable
fun ReviewStatistics(
    avgStarRating: Double,
    starRatings: List<Int>
) {
    val totalReviewCount = starRatings.sum()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = avgStarRating.toString(),
            style = Title
        )

        Spacer(modifier = Modifier.width(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            for (i in 5 downTo 1) { // 별점 별 개수 별로 변수 -> 배열화?
                val reviewCount = starRatings[i - 1]

                Row {
                    ReviewStar(
                        starCnt = i,
                        spaceBy = (4.83).dp,
                        size = 10.dp
                    )

                    Spacer(modifier = Modifier.width(7.dp))

                    reviewProgressBar(
                        cnt = reviewCount,
                        totalCnt = totalReviewCount,
                        modifier = Modifier
                            .width(126.dp)
                            .height(6.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFFE9E9E9),
                                shape = RoundedCornerShape(8.dp)
                            )
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "($reviewCount)",
                        fontFamily = MainFont,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}