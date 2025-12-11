package com.tlog.data.dto.response.restaurant

import com.google.gson.annotations.SerializedName

data class RestaurantDto(
    val place_name: String,
    val category_name: String,
    val phone: String? = null,
    val address_name: String,
    val road_address_name: String,
    val longitude: String,
    val latitude: String,
    val place_url: String,
    val distance: String,
    val images: List<PlaceImage> = emptyList(),
    val blogs: List<BlogPost> = emptyList()
)
