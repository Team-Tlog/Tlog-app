package com.tlog.data.model.request.sns

data class PostWriteBody(
    val author: String,
    val courseId: String,
    val content: String,
    val imageUrls: List<String>
)
