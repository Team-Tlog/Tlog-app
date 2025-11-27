package com.tlog.viewmodel.travel

import android.content.Context
import android.net.Uri
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.local.TokenProvider
import com.tlog.data.model.request.travel.AddTravelRequest
import com.tlog.data.model.share.Location
import com.tlog.data.repository.AddTravelRepository
import com.tlog.data.util.FirebaseImageUploader
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

@HiltViewModel
class AddTravelViewModel @Inject constructor(
    private val repository: AddTravelRepository,
    tokenProvider: TokenProvider
): BaseViewModel() {

    private var userId: String? = null

    private val _travelName = MutableStateFlow("")
    val travelName = _travelName.asStateFlow()

    private val _travelAddress = MutableStateFlow("")
    val travelAddress = _travelAddress.asStateFlow()

    private val _hasParking = MutableStateFlow(false)
    val hasParking = _hasParking.asStateFlow()

    private val _isPetFriendly = MutableStateFlow(false)
    val isPetFriendly = _isPetFriendly.asStateFlow()

    private val _hashTag = MutableStateFlow("")
    val hashTag = _hashTag.asStateFlow()

    private val _hashTags = MutableStateFlow<List<String>>(emptyList())
    val hashTags = _hashTags.asStateFlow()

    private val _travelDescription = MutableStateFlow("")
    val travelDescription = _travelDescription.asStateFlow()

    private val _imageUri = MutableStateFlow(Uri.EMPTY)
    val imageUri = _imageUri.asStateFlow()

    init {
        userId = tokenProvider.getUserId()
    }

    suspend fun imageUpload(context: Context, imageUri: Uri, city: String, district: String): String {
        // 이미지 업로드를 병렬로 처리
        return FirebaseImageUploader.uploadWebpImage(
            context,
            imageUri,
            "images/${city}/${district}/${System.currentTimeMillis()}_${UUID.randomUUID()}.webp"
        )
    }

    fun addNewTravel(context: Context) {
        val safeUserId = userId ?: return

        launchSafeCall(
            action = {
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
                showToast("여행지 등록 성공, 관리자 승인을 기다려주세요.")
                navigate(Screen.Main, true)
            }
        )
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