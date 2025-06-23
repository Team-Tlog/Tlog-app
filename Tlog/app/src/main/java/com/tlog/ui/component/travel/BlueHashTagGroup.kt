package com.tlog.ui.component.travel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tlog.ui.component.share.BlueHashTag

@Composable
fun BlueHashTagGroup(
    hashTags: List<String>,
    space: Dp = 8.dp,
    maxCnt: Int = Int.MAX_VALUE
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space)
    ) {
        for (i in hashTags.indices) {
            if (i + 1 > maxCnt)
                break

            val tag = hashTags[i]

            BlueHashTag(tag = tag)
        }
    }
}