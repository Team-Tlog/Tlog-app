package com.tlog.ui.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.BackgroundBlue
import com.tlog.ui.theme.Essential
import com.tlog.ui.theme.MainFont

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

            Box(
                modifier = Modifier
                    //.fillMaxWidth()
                    .clip(RoundedCornerShape(4))
                    .background(BackgroundBlue)
            ) {
                Box(
                    modifier = Modifier
                        //.fillMaxSize()
                        .height(22.dp)
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tag,
                        fontSize = 10.sp,
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        color = Essential
                    )
                }
            }
        }
    }
}