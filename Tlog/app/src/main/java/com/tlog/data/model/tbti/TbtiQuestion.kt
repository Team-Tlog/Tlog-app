package com.tlog.data.model.tbti


data class TbtiQuestion(
    val id: String,
    val content: String,
    val weight: Int,
    val traitCategory: String,
    val categoryIntial: String,
    val answers: List<TbtiAnswer>
)