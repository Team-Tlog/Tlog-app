package com.tlog.data.model.share

data class TbtiDescription(
    val tbtiString: String,
    val imageUrl: String?,
    val secondName: String,
    val description: String,
    val preferredTbti: String,
    val notPreferredTbti: String
)