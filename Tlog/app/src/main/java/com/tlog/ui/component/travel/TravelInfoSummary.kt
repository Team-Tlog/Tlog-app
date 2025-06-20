package com.tlog.ui.component.travel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.data.api.TravelDetailResponse
import com.tlog.ui.component.share.HashTagsGroup
import com.tlog.ui.style.Body1Regular
import com.tlog.ui.style.Body2Regular
import com.tlog.ui.theme.MainFont


@Composable
fun TravelInfoSummary(
    travelInfo: TravelDetailResponse
) {
    Column {
        Text(
            text = travelInfo.name,
            fontFamily = MainFont,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(3.dp))

        Box {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_map_pin),
                    contentDescription = "위치 ${travelInfo.city}",
                    tint = Color.Black
                )

                Text(
                    text = travelInfo.city,
                    style = Body2Regular
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_filled_star),
                    contentDescription = "${travelInfo.city} 별점",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(14.dp)
                )

                Text(
                    text = travelInfo.averageRating.toString(),
                    style = Body2Regular
                )

                Text(
                    text = "(${travelInfo.reviewCount})", // 추후 api명세서 나오면 제대로 수정하기
                    style = Body2Regular
                )
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        HashTagsGroup(
            hashTags = travelInfo.topTags.map { it.tagName },
            space = 4.dp
        )

        Spacer(modifier = Modifier.height(57.dp))

        Text(
            text = travelInfo.description,
            style = Body1Regular
        )
    }
}