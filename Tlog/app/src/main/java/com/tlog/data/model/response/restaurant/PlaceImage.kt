package com.tlog.data.model.response.restaurant

import com.google.gson.annotations.SerializedName

data class PlaceImage(
    val title: String,
    val link: String,
    val thumbnail: String,
    @SerializedName("sizeheight")
    val sizeHeight: String,
    @SerializedName("sizewidth")
    val sizeWidth: String
)
