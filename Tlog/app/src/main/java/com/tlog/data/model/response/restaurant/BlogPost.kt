package com.tlog.data.model.response.restaurant

import com.google.gson.annotations.SerializedName

data class BlogPost(
    val title: String,
    val link: String,
    val description: String,
    @SerializedName("bloggername")
    val bloggerName: String,
    @SerializedName("postdate")
    val postDate: String
)
