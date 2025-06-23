package com.tlog.ui.component.sns

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.R
import com.tlog.ui.style.Body2Regular
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.sns.SnsPostViewModel


@Composable
fun RecentTravelCourseGroup(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "최근 다녀온 코스",
            style = Body2Regular,
            modifier = Modifier
                .padding(start = 4.dp)
        )

        RecentTravelCourse()

        OtherCourseSection()
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
        itemsIndexed(viewModel.recentTravelCourses.value) { idx, item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clickable {
                        viewModel.updateSelectedCourse(idx)
                    }
            ) {
                Image(
                    painter = painterResource(item.pictureList[0]),
                    contentDescription = "{${item.city}여행지}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray)
                        .border(2.dp, if (viewModel.selectedCourse.value == idx) MainColor else Color.Unspecified, RoundedCornerShape(15.dp))
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = item.city,
                        style =
                            if (viewModel.selectedCourse.value == idx)
                                TextStyle(
                                    fontFamily = MainFont,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            else
                                Body2Regular
                )
            }
        }
    }
}

@Composable
fun OtherCourseSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Log.d("다른 코스", "my click!!")
            }
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "다른 코스",
            fontFamily = MainFont,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MainColor
        )

        Icon(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = "다른 코스",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(20.dp)
        )
    }
}