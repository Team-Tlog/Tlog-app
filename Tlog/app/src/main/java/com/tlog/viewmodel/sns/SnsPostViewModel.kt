package com.tlog.viewmodel.sns

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.R
import com.tlog.data.model.sns.TravelCourse


class SnsPostViewModel: BaseViewModel() {
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

    private var _selectImages = mutableStateOf<List<Uri>>(emptyList())
    val selectImages: State<List<Uri>> = _selectImages

    private var _selectedCourse = mutableStateOf(1)
    val selectedCourse = _selectedCourse

    private var _postContent = mutableStateOf("")
    val postContent: State<String> = _postContent

    fun updatePostContent(content: String) {
        _postContent.value = content
    }

    fun updateSelectImages(uri: Uri) {
        if (_selectImages.value.contains(uri))
            _selectImages.value = _selectImages.value - uri // += ?
        else
            _selectImages.value = _selectImages.value + uri // += ?
    }

    fun selectImagesIn(uri: Uri): Boolean {
        if (_selectImages.value.contains(uri))
            return true
        return false
    }

    fun updateSelectedCourse(idx: Int) {
        _selectedCourse.value = idx
    }
}