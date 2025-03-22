package com.tlog.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class ReviewViewModel: ViewModel() {
    var starCnt by mutableStateOf(0)
    var review by mutableStateOf("")
    var hashTag by mutableStateOf("")
    var hashTags by mutableStateOf<List<String>>(listOf("단풍", "관광")) // 테스트용 2개 추후 로직 생성 시 삭제할 것
    var imageList by mutableStateOf<List<Uri>>(emptyList())


    fun updateStarCnt(newStarCnt: Int) {
        starCnt = newStarCnt
    }

    fun updateReview(newReview: String) {
        review = newReview
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