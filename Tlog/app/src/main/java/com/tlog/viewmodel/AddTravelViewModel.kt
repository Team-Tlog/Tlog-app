package com.tlog.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddTravelViewModel: ViewModel() {
    var travelName by mutableStateOf("")
    var travelAddress by mutableStateOf("")
    var hasParking by mutableStateOf(false)
    var isPetFriendly by mutableStateOf(false)
    var hashTag by mutableStateOf("")
    var hashTags by mutableStateOf<List<String>>(listOf("단풍", "관광")) // 테스트용 2개 추후 로직 생성 시 삭제할 것
    var travelDescription by mutableStateOf("")
    var imageList by mutableStateOf<List<Uri>>(emptyList())




    fun updateTravelName(newTravelName: String) {
        travelName = newTravelName
    }

    fun updateTravelAddress(newTravelAddress: String) {
        travelAddress = newTravelAddress
    }

    fun updateHasParking(newHasParking: Boolean) {
        hasParking = newHasParking
    }

    fun updateIsPetFriendly(newIsPetFreindlyName: Boolean) {
        isPetFriendly = newIsPetFreindlyName
    }

    fun updateTravelDescription(newTravelDescription: String) {
        travelDescription = newTravelDescription
    }

    fun updateHashTag(newHashTag: String) {
        hashTag = newHashTag
    }

    fun addHashTag(hashTag: String) {
        hashTags += hashTag
    }

    fun clearHashTags() {
        hashTags = emptyList()
    }

    fun addImage(uri: Uri) {
        imageList += uri
    }

    fun clearImages() {
        imageList = emptyList()
    }
}


