package com.tlog.viewmodel.review

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class ReviewViewModel: ViewModel() {
    private var _starCnt = mutableStateOf(0)
    val starCnt = _starCnt
    private var _review = mutableStateOf("")
    val review = _review
    private var _hashTag = mutableStateOf("")
    val hashTag = _hashTag
    private var _hashTags = mutableStateOf<List<String>>(listOf("단풍", "관광")) // 테스트용 2개 추후 로직 생성 시 삭제할 것
    val hashTags = _hashTags
    private var _imageList = mutableStateOf<List<Uri>>(emptyList())
    val imageList = _imageList


    fun updateStarCnt(newStarCnt: Int) {
        _starCnt.value = newStarCnt
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