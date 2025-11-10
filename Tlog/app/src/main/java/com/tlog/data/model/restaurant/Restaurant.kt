package com.tlog.data.model.restaurant

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("place_name")
    val placeName: String,
    @SerializedName("category_name")
    val categoryName: String,
    val phone: String? = null,
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("road_address_name")
    val roadAddressName: String,
    val longitude: String,
    val latitude: String,
    @SerializedName("place_url")
    val placeUrl: String,
    val distance: String,
    val images: List<PlaceImage> = emptyList(),
    val blogs: List<BlogPost> = emptyList()
)

data class PlaceImage(
    val title: String,
    val link: String,
    val thumbnail: String,
    @SerializedName("sizeheight")
    val sizeHeight: String,
    @SerializedName("sizewidth")
    val sizeWidth: String
)

data class BlogPost(
    val title: String,
    val link: String,
    val description: String,
    @SerializedName("bloggername")
    val bloggerName: String,
    @SerializedName("postdate")
    val postDate: String
)