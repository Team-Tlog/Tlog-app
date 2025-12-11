package com.tlog.viewmodel.review

import android.content.Context
import android.net.Uri
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.dto.request.review.ReviewRequest
import com.tlog.data.repository.ReviewRepository
import com.tlog.data.util.FirebaseImageUploader
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import java.util.UUID
import com.tlog.data.local.TokenProvider
import com.tlog.ui.navigation.Screen
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ReviewWriteViewModel @Inject constructor(
    private val repository: ReviewRepository,
    tokenProvider: TokenProvider
): BaseViewModel() {
    private var userId: String? = null
    private val _rating = MutableStateFlow(0)
    val rating = _rating.asStateFlow()
    private val _review = MutableStateFlow("")
    val review = _review.asStateFlow()
    private val _hashTag = MutableStateFlow("")
    val hashTag = _hashTag.asStateFlow()
    private val _hashTags = MutableStateFlow<List<String>>(emptyList())
    val hashTags = _hashTags.asStateFlow()
    private val _images = MutableStateFlow<List<Uri>>(emptyList())
    val images = _images.asStateFlow()

    init {
        userId = tokenProvider.getUserId()
    }

    suspend fun imageUpload(context: Context, imageUriList: List<Uri>): List<String> {
        // 이미지 업로드를 병렬로 처리
        return imageUriList.map { uri ->
            viewModelScope.async {
                FirebaseImageUploader.uploadWebpImage(
                    context,
                    uri,
                    "images/review/${System.currentTimeMillis()}_${UUID.randomUUID()}.webp"
                )
            }
        }.awaitAll()

        // 직렬
//        val returnList = mutableListOf<String>()
//        imageUriList.forEach { uri ->
//            returnList.add(FirebaseImageUploader.uploadWebpImage(
//                    context,
//                    uri,
//                    "images/review/${System.currentTimeMillis()}_${UUID.randomUUID()}.webp"
//                )
//            )
//
//
//        }
//
//        return returnList

    }

    fun addReview(context: Context, travelId: String) {
        val safeUserId = userId ?: return // null이면 return

        launchSafeCall(
            action = {
                val imageUrlList = imageUpload(context, images.value)
                repository.addReview(
                    ReviewRequest(
                        userId = safeUserId,
                        destinationId = travelId,
                        username = "tmp",
                        rating = rating.value,
                        content = review.value,
                        imageUrlList = imageUrlList,
                        customTagNames = hashTags.value
                    )
                )
                showToast("리뷰 작성 성공")
                navigate(Screen.Main, true)
            }
        )
    }

    fun inputCheck(): Int {
        if (_rating.value == 0)
            return 1
        if (_review.value.isEmpty() || _review.value.isBlank())
            return 2
        return 0
    }


    fun updateRating(newRating: Int) {
        _rating.value = newRating
    }

    fun updateReview(newReview: String) {
        _review.value = newReview
    }

    fun updateHashTag(newHashTag: String) {
        _hashTag.value = newHashTag
    }

    fun addHashTag(hashTag: String) {
        _hashTags.value += hashTag
    }

    fun addImage(uri: Uri) {
        _images.value += uri
    }

    fun checkInput(): Int {
        if (_images.value.isEmpty())
            return 1
        if (_rating.value == 0)
            return 2
        if (_review.value.isEmpty() || _review.value.isBlank())
            return 3
        return 0
    }

    fun clearHashTags() {
        _hashTags.value = emptyList()
    }

    fun clearImages() {
        _images.value = emptyList()
    }
}
