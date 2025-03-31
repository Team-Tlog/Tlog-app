package com.tlog.ui.component.share

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tlog.R


@Composable
fun StarRating(
    rating: Int,
    onStarClicked: (Int) -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                painter = painterResource(
                    id = if (i <= rating) R.drawable.ic_filled_star else R.drawable.ic_empty_star
                ),
                contentDescription = "별점 $i",
                tint = Color.Unspecified, // 원래 색깔 유지
                modifier = Modifier
                    .clickable { onStarClicked(i) }
                    .padding(start = 4.dp)
            )
        }
    }
}