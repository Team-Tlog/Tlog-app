package com.tlog.viewmodel.sns

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.R
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.CourseItem
import com.tlog.data.model.sns.TravelCourse
import com.tlog.data.repository.SnsPostRepository
import com.tlog.data.util.FirebaseImageUploader
import com.tlog.ui.theme.DefaultImage
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.UUID


@HiltViewModel
class SnsPostViewModel @Inject constructor(
    private val snsPostRepository: SnsPostRepository,
    tokenProvider: TokenProvider
) : BaseViewModel() {
    private var userId = ""
    private var _recentTravelCourses = MutableStateFlow<List<CourseItem>>(emptyList())
    val recentTravelCourses: StateFlow<List<CourseItem>> = _recentTravelCourses

    private var _selectImages = mutableStateOf<List<Uri>>(emptyList())
    val selectImages: State<List<Uri>> = _selectImages

    private var _selectedCourse = mutableStateOf(1)
    val selectedCourse = _selectedCourse

    private var _postContent = mutableStateOf("")
    val postContent: State<String> = _postContent

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

    fun selectImagesIn(uri: Uri): Boolean {
        if (_selectImages.value.contains(uri))
            return true
        return false
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
