package com.tlog.data.dto.tbti


data class TbtiQuestionDto(
    val id: String,
    val content: String,
    val weight: Int,
    val traitCategory: String,
    val categoryIntial: String,
    val answers: List<TbtiAnswerDto>
)
