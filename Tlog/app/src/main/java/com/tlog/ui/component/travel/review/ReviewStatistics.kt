package com.tlog.ui.component.travel.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont


@Composable
fun ReviewStatistics(
    avgStarRating: Double,
    ratingDistribution: Map<String, Int>
) {
    val totalReviewCount = ratingDistribution.values.sum()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min) // 자식의 크기에 맞게 -> 측정 복잡도 높음 많이 쓰면 안좋음
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = avgStarRating.toString(),
                style = TextStyle(
                    fontFamily = MainFont,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainColor
                )
            )

            Spacer(modifier = Modifier.height(9.dp))

            ReviewStar(
                starCnt = avgStarRating.toInt(), // toInt = 버림
                spaceBy = 3.dp,
                size = 15.dp
            )
        }

        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 18.dp),
            thickness = 1.dp,
            color = Color(0xFFE3E3E3)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            for (i in 5 downTo 1) {
                val reviewCount = ratingDistribution[i.toString()] ?: 0

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = i.toString() + "점",
                        style = TextStyle(
                            fontFamily = MainFont,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFADADAD)
                        )
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    ReviewProgressBar(
                        cnt = reviewCount,
                        totalCnt = totalReviewCount,
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                    )

                }
            }
        }
    }
}