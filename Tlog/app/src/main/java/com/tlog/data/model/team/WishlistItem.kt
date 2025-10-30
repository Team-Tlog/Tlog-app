package com.tlog.data.model.team

import com.tlog.data.model.share.Location

data class WishlistItem(
    val id: String,
    val name: String,
    val location: Location,
    val imageUrl: String,
    val description: String,
    val tagCountList: List<TagCount>
)