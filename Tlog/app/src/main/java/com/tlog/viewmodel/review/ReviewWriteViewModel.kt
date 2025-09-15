package com.tlog.viewmodel.review

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.tlog.viewmodel.base.BaseViewModel
import coil.network.HttpException
import com.tlog.data.api.ReviewRequest
import com.tlog.data.repository.ReviewRepository
import com.tlog.data.util.FirebaseImageUploader
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import java.util.UUID
import com.tlog.api.retrofit.TokenProvider
import com.tlog.ui.navigation.Screen
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

@HiltViewModel
class ReviewWriteViewModel @Inject constructor(
    private val repository: ReviewRepository,
    tokenProvider: TokenProvider
): BaseViewModel() {

    private var userId: String? = null

    init {
        userId = tokenProvider.getUserId()
    }

    private var _rating = mutableIntStateOf(0)
    val rating: State<Int> = _rating
    private var _review = mutableStateOf("")
    val review: State<String> = _review
    private var _hashTag = mutableStateOf("")
    val hashTag: State<String> = _hashTag
    private var _hashTags = mutableStateOf<List<String>>(emptyList()) // 테스트용 2개 추후 로직 생성 시 삭제할 것
    val hashTags: State<List<String>> = _hashTags
    private var _imageList = mutableStateOf<List<Uri>>(emptyList())
    val imageList: State<List<Uri>> = _imageList


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
                val imageUrlList = imageUpload(context, imageList.value)
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
        if (_rating.intValue == 0)
            return 1
        if (_review.value.isEmpty() || _review.value.isBlank())
            return 2
        return 0
    }


    fun updateRating(newRating: Int) {
        _rating.intValue = newRating
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
        _imageList.value += uri
    }

    fun checkInput(): Int {
        if (_imageList.value.isEmpty())
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
        _imageList.value = emptyList()
    }
}
