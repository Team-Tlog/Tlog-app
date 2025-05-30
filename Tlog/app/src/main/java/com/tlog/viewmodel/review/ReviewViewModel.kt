package com.tlog.viewmodel.review

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.TravelApi
import com.tlog.data.api.ReviewRequest
import com.tlog.data.repository.ReviewRepository
import com.tlog.data.util.FirebaseImageUploader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.UUID

import com.google.firebase.auth.FirebaseAuth
import com.tlog.api.retrofit.TokenProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val repository: ReviewRepository,
    tokenProvider: TokenProvider
): ViewModel() {
    private var userId: String? = null

    init {
        userId = tokenProvider.getUserId()
    }
    
    // api 결과에 따른 이벤트 값
    sealed class UiEvent {
        object ReviewSuccess: UiEvent()
        data class ReviewError(val message: String): UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var _rating = mutableStateOf(0)
    val rating = _rating
    private var _review = mutableStateOf("")
    val review = _review
    private var _hashTag = mutableStateOf("")
    val hashTag = _hashTag
    private var _hashTags = mutableStateOf<List<String>>(emptyList()) // 테스트용 2개 추후 로직 생성 시 삭제할 것
    val hashTags = _hashTags
    private var _imageList = mutableStateOf<List<Uri>>(emptyList())
    val imageList = _imageList


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
            Log.d("auth", FirebaseAuth.getInstance().currentUser?.uid ?: "로그인 안됨")

            try {
                val imageUrlList = imageUpload(context, imageList.value)
                val result = repository.addReview(
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
                when (result.status) {
                    201 -> _eventFlow.emit(UiEvent.ReviewSuccess)
                    400 -> _eventFlow.emit(UiEvent.ReviewError("올바른 값을 입력해 주세요."))
                    500 -> _eventFlow.emit(UiEvent.ReviewError("서버 오류가 발생했습니다."))
                    else -> _eventFlow.emit(UiEvent.ReviewError("알 수 없는 오류가 발생했습니다."))
                }
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ReviewError("네트워크 오류가 발생했습니다. ${e.message}"))
            }
        }
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

@Module
@InstallIn(SingletonComponent::class)
object ReviewModule {
    @Provides
    fun provideReviewRepository(
        travelApi: TravelApi
    ): ReviewRepository {
        return ReviewRepository(travelApi)
    }
}