package com.tlog.viewmodel.sns

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tlog.R
import com.tlog.data.model.sns.TravelCourse


class SnsPostViewModel: ViewModel() {
    private var _recentTravelCourses = mutableStateOf<List<TravelCourse>>(
        listOf(
            TravelCourse(
                city = "제주",
                pictureList = listOf(
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju
                )
            ),
            TravelCourse(
                city = "부산",
                pictureList = listOf(
                    R.drawable.test_image,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju
                )
            ),
            TravelCourse(
                city = "대구",
                pictureList = listOf(
                    R.drawable.destination_img,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju
                )
            ),
            TravelCourse(
                city = "서울",
                pictureList = listOf(
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju
                )
            ),
            TravelCourse(
                city = "광주",
                pictureList = listOf(
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju,
                    R.drawable.tmp_jeju
                )
            )
        )
    )
    val recentTravelCourses: State<List<TravelCourse>> = _recentTravelCourses


}