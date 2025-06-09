package com.tlog.viewmodel.sns

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.SnsPost
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.repository.SnsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SnsDetailViewModel @Inject constructor(
    private val repository: SnsRepository,
    tokenProvider: TokenProvider
) : ViewModel() {

    var userId: String? = ""

    init {
        userId = tokenProvider.getUserId()
    }


    private var _post = mutableStateOf<SnsPost?>(null)
    val post: State<SnsPost?> = _post

    private var _comment = mutableStateOf<String>("")
    val comment: State<String> = _comment

    fun getPostDetail(postId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getPost(postId)

                _post.value = result.data
            } catch (e: Exception) {
                Log.d("SnsDetailViewModel", e.message.toString())
            }
        }
    }

    fun addComment() {
        Log.d("okhttp", "hihi")
        viewModelScope.launch {
            try {
                val result = repository.createComment(postId = post.value!!.postId, author = userId!!, content = comment.value)
                when(result.status) {
                    200 -> {
                        _comment.value = ""
                        getPostDetail(post.value!!.postId)
                    }
                    else -> {
                        Log.d("SnsDetailViewModel", result.message)
                    }
                }
            } catch (e: Exception) {
                Log.d("SnsDetailViewModel", e.message.toString())
            }
        }
    }

    fun updateComment(value: String) {
        _comment.value = value
    }
}