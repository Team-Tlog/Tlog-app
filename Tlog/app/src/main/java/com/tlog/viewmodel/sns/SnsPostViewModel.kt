package com.tlog.viewmodel.sns

import android.content.Context
import android.net.Uri
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.local.TokenProvider
import com.tlog.data.dto.response.course.CourseDto
import com.tlog.data.repository.SnsPostRepository
import com.tlog.data.util.FirebaseImageUploader
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID


@HiltViewModel
class SnsPostViewModel @Inject constructor(
    private val snsPostRepository: SnsPostRepository,
    tokenProvider: TokenProvider
) : BaseViewModel() {
    private var userId = ""
    private var _recentTravelCourses = MutableStateFlow<List<CourseDto>>(emptyList())
    val recentTravelCourses = _recentTravelCourses.asStateFlow()

    private var _selectImages = MutableStateFlow<List<Uri>>(emptyList())
    val selectImages = _selectImages.asStateFlow()

    private var _selectedCourse = MutableStateFlow(1)
    val selectedCourse = _selectedCourse.asStateFlow()

    private var _postContent = MutableStateFlow("")
    val postContent = _postContent.asStateFlow()

    init {
        userId = tokenProvider.getUserId() ?: ""

        getUserTravelCourses()
    }

    fun updatePostContent(content: String) {
        _postContent.value = content
    }

    fun getUserTravelCourses() {
        launchSafeCall(
            action = {
                snsPostRepository.getCourses(userId)
            },
            onSuccess = {
                _recentTravelCourses.value = it.data
            }
        )
    }

    fun updateSelectImages(uri: Uri) {
        if (_selectImages.value.contains(uri))
            _selectImages.value = _selectImages.value - uri // += ?
        else
            _selectImages.value = _selectImages.value + uri // += ?
    }

    fun updateSelectedCourse(idx: Int) {
        _selectedCourse.value = idx
    }

    private fun clearViewModel() {
        _selectImages.value = emptyList()
        _selectedCourse.value = 1
        _postContent.value = ""
    }

    fun postWrite(context: Context) {
        launchSafeCall(
            action = {
                val imageUrls = selectImages.value.map {
                    FirebaseImageUploader.uploadWebpImage(
                        context = context,
                        uri = it,
                        path = "images/post/${System.currentTimeMillis()}_${UUID.randomUUID()}.webp"
                    )
                }

                // return 사용하지 않음
                snsPostRepository.postWrite(
                    userId = userId,
                    courseId = recentTravelCourses.value[selectedCourse.value].id,
                    content = postContent.value,
                    imageUrls = imageUrls
                )

//                UiEvent.ShowToast("게시물 등록 성공") -> 발표 후 뷰모델 분리 ㄱㄱ
//                UiEvent.PopBackStack(2)
                clearViewModel()
            }
        )
    }
}
