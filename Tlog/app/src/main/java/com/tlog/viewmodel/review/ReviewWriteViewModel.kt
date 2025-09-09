package com.tlog.viewmodel.review

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.tlog.data.api.ReviewRequest
import com.tlog.data.repository.ReviewRepository
import com.tlog.data.util.FirebaseImageUploader
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import java.util.UUID
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.model.share.toErrorMessage
import com.tlog.ui.navigation.Screen
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class ReviewWriteViewModel @Inject constructor(
    private val repository: ReviewRepository,
    tokenProvider: TokenProvider
): ViewModel() {
    sealed interface UiEvent {
        data class Navigate(val target: Screen, val clearBackStack: Boolean = false): UiEvent
        data class ShowToast(val message: String): UiEvent
    }

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

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
        viewModelScope.launch {
            val safeUserId = userId ?: return@launch // null이면 launch 종료 (안돌아감)

            try {
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
                _uiEvent.trySend(UiEvent.ShowToast("리뷰 작성 성공"))
//                delay(500) -> 채널의 버퍼를 지정하지 않으면 사이즈가 0이라 동시에 들어가면 무시될 수 있으나 버퍼를 지정하면 큐에 들어가기 때문에 딜레이 따로 넣을 필요 X
                _uiEvent.trySend(UiEvent.Navigate(Screen.Main, true))
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
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
