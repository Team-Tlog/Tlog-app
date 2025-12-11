package com.tlog.data.dto.tbti

data class TbtiDescriptionDto(
    val tbtiString: String,
    val imageUrl: String?,
    val secondName: String,
    val description: String,
    val preferredTbti: String,
    val notPreferredTbti: String
)
