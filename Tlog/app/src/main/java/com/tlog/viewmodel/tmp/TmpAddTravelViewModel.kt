package com.tlog.viewmodel.tmp

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TmpAddTravelViewModel: ViewModel() {
    private var _travelName = mutableStateOf("")
    val travelName = _travelName
    private var _travelAddress = mutableStateOf("")
    val travelAddress = _travelAddress
    private var _hasParking = mutableStateOf(false)
    val hasParking = _hasParking
    private var _isPetFriendly = mutableStateOf(false)
    val isPetFriendly = _isPetFriendly
    private var _hashTag = mutableStateOf("")
    val hashTag = _hashTag
    private var _hashTags =
        mutableStateOf<List<String>>(listOf("단풍", "관광")) // 테스트용 2개 추후 로직 생성 시 삭제할 것
    val hashTags = _hashTags
    private var _travelDescription = mutableStateOf("")
    val travelDescription = _travelDescription
    private var _imageList = mutableStateOf<List<Uri>>(emptyList())
    val imageList = _imageList



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
        _imageList.value += uri
    }

    fun clearImages() {
        _imageList.value = emptyList()
    }
}