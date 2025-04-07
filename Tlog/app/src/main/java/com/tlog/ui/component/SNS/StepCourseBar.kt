package com.tlog.ui.component.SNS

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tlog.ui.style.Body2Regular


@Composable
fun StepCourseBar(
    courseTitles: List<String>,
    activeColor: Color = Color.Blue,
    lineHeight: Dp = 4.dp,
    circleSize: Dp = 16.dp,
    maxVisibleSteps: Int = 4,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val visibleSteps = courseTitles.size.coerceAtMost(maxVisibleSteps)
    val itemWidth = (screenWidth - 32.dp) / visibleSteps

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalAlignment = Alignment.Top
    ) {
        items(courseTitles.size) { stepIndex ->
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .width(itemWidth)
            ) {
                Text(
                    text = courseTitles[stepIndex],
                    style = Body2Regular,
                    modifier = Modifier.padding(bottom = 7.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 점
                    Box(
                        modifier = Modifier
                            .size(circleSize)
                            .background(color = activeColor, shape = CircleShape)
                    )

                    // 마지막 점 뒤에는 선 없음
                    if (stepIndex < courseTitles.lastIndex) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(lineHeight)
                                .background(activeColor)
                        )
                    }
                }
            }
        }
    }
}