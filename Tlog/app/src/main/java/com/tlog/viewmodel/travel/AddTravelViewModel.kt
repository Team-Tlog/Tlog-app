package com.tlog.viewmodel.travel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.AddTravelRequest
import com.tlog.data.model.share.Location
import com.tlog.data.repository.AddTravelRepository
import com.tlog.data.util.FirebaseImageUploader
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.UUID

@HiltViewModel
class AddTravelViewModel @Inject constructor(
    private val repository: AddTravelRepository,
    tokenProvider: TokenProvider
): ViewModel() {
    private var userId: String? = null

    init {
        userId = tokenProvider.getUserId()
    }


    private var _travelName = mutableStateOf("")
    val travelName: State<String> = _travelName

    private var _travelAddress = mutableStateOf("")
    val travelAddress: State<String> = _travelAddress

    private var _hasParking = mutableStateOf(false)
    val hasParking: State<Boolean> = _hasParking

    private var _isPetFriendly = mutableStateOf(false)
    val isPetFriendly: State<Boolean> = _isPetFriendly

    private var _hashTag = mutableStateOf("")
    val hashTag: State<String> = _hashTag

    private var _hashTags =
        mutableStateOf<List<String>>(emptyList())
    val hashTags: State<List<String>> = _hashTags

    private var _travelDescription = mutableStateOf("")
    val travelDescription: State<String> = _travelDescription

    private var _imageUri = mutableStateOf(Uri.EMPTY)
    val imageUri: State<Uri> = _imageUri


    sealed class UiEvent {
        object ApiSuccess: UiEvent()
        data class ApiError(val message: String): UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    suspend fun imageUpload(context: Context, imageUri: Uri, city: String, district: String): String {
        // 이미지 업로드를 병렬로 처리
        return FirebaseImageUploader.uploadWebpImage(
                    context,
                    imageUri,
                    "images/${city}/${district}/${System.currentTimeMillis()}_${UUID.randomUUID()}.webp"
                )
        }

    fun addNewTravel(context: Context) {
        viewModelScope.launch {
            val safeUserId = userId ?: return@launch

            try {
                val imageUrl = imageUpload(context, imageUri.value, "city", "district")

                val result = repository.addTravel(
                    AddTravelRequest(
                        creater = safeUserId,
                        name = travelName.value,
                        address = travelAddress.value,
                        location = Location("0.0", "0.0"),
                        city = "임시", // 시, 도
                        district = "임시", // 시, 군, 구
                        hasParking = hasParking.value,
                        petFriendly = isPetFriendly.value,
                        imageUrl = imageUrl,
                        description = travelDescription.value,
                        customTags = hashTags.value
                    )
                )

                when (result.status) {
                    201 -> _eventFlow.emit(UiEvent.ApiSuccess)
                    409 -> _eventFlow.emit(UiEvent.ApiError("이미 존재하는 여행지 입니다."))
                    500 -> _eventFlow.emit(UiEvent.ApiError("서버 오류가 발생했습니다."))
                    else -> _eventFlow.emit(UiEvent.ApiError("알 수 없는 오류가 발생했습니다."))
                }
            }
            catch (e: Exception) {
                Log.e("AddTravel", "알 수 없는 오류", e)
            }
        }
    }


    fun updateTravelName(newTravelName: String) {
        _travelName.value = newTravelName
    }

    fun updateTravelAddress(newTravelAddress: String) {
        _travelAddress.value = newTravelAddress
    }

    fun updateHasParking(newHasParking: Boolean) {
        _hasParking.value = newHasParking
    }

    fun updateIsPetFriendly(newIsPetFreindlyName: Boolean) {
        _isPetFriendly.value = newIsPetFreindlyName
    }

    fun updateTravelDescription(newTravelDescription: String) {
        _travelDescription.value = newTravelDescription
    }

    fun updateHashTag(newHashTag: String) {
        _hashTag.value = newHashTag
    }

    fun addHashTag(hashTag: String) {
        _hashTags.value += hashTag
    }

    fun clearHashTags() {
        _hashTags.value = emptyList()
    }

    fun addImage(uri: Uri) {
        _imageUri.value = uri
    }

    fun clearImages() {
        _imageUri.value = Uri.EMPTY
    }

    fun checkInput(): Boolean {
        if (travelName.value.isEmpty() || travelAddress.value.isEmpty() || travelDescription.value.isEmpty())
            return false
        return true
    }
}