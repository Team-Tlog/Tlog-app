package com.tlog.data.dto.response.restaurant

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
