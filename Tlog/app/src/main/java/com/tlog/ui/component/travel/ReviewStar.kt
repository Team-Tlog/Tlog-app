package com.tlog.ui.component.travel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tlog.R


@Composable
fun ReviewStar(
    starCnt: Int,
    spaceBy: Dp,
    size: Dp
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spaceBy)
    ) {
        for (i in 1..starCnt) {
            Icon(
                painter = painterResource(R.drawable.ic_filled_star),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(size)
            )
        }

        for (i in starCnt + 1..5) {
            Icon(
                painter = painterResource(R.drawable.ic_empty_star),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(size)
            )
        }
    }
}