package com.tlog.viewmodel.travel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.AddTravelRequest
import com.tlog.data.model.share.Location
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.repository.AddTravelRepository
import com.tlog.data.util.FirebaseImageUploader
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.UUID

@HiltViewModel
class AddTravelViewModel @Inject constructor(
    private val repository: AddTravelRepository,
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

                repository.addTravel(
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
                clearImages()
                clearHashTags()
                _uiEvent.trySend(UiEvent.ShowToast("여행지 등록 성공"))
                _uiEvent.trySend(UiEvent.Navigate(Screen.Main, true))
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
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

    private fun clearHashTags() {
        _hashTags.value = emptyList()
    }

    fun addImage(uri: Uri) {
        _imageUri.value = uri
    }

    private fun clearImages() {
        _imageUri.value = Uri.EMPTY
    }

    fun checkInput(): Boolean {
        if (travelName.value.isEmpty() || travelAddress.value.isEmpty() || travelDescription.value.isEmpty())
            return false
        return true
    }
}