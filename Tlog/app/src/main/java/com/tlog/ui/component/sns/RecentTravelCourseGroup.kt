package com.tlog.ui.component.sns

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.style.Body2Regular
import com.tlog.viewmodel.sns.SnsPostViewModel


@Composable
fun RecentTravelCourseGroup(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "최근 다녀온 코스",
            style = Body2Regular,
            modifier = Modifier
                .padding(start = 4.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        RecentTravelCourse()
    }
}

@Composable
fun RecentTravelCourse(
    viewModel: SnsPostViewModel = viewModel()
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(vertical = 10.dp)
    ) {
        items(viewModel.recentTravelCourses.value) { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
            ) {
                Image(
                    painter = painterResource(item.pictureList[0]),
                    contentDescription = "{${item.city}여행지}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray)
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = item.city,
                    style = Body2Regular
                )
            }
        }
    }
}