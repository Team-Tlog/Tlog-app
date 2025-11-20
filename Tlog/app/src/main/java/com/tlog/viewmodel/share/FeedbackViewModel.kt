package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.tlog.data.api.FeedbackRequest
import com.tlog.data.repository.MyPageRepository
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val repository: MyPageRepository
): BaseViewModel() {
    private var _title = mutableStateOf("")
    val title: State<String> = _title

    private var _content = mutableStateOf("")
    val content: State<String> = _content

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateContent(newContent: String) {
        _content.value = newContent
    }

    fun checkInput(): Boolean {
        return _title.value.isNotEmpty() && _content.value.isNotEmpty()
    }

    fun submitFeedback() {
        if (!checkInput()) return

        launchSafeCall(
            action = {
                val request = FeedbackRequest(
                    title = _title.value,
                    content = _content.value,
                    refImageUrls = emptyList() // 이미지는 추후 추가
                )
                repository.submitFeedback(request)
                showToast("피드백이 전송되었습니다")
                popBackStack()
            }
        )
    }
}