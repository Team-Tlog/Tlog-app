package com.tlog.data.api

data class TbtiQuestionResponse(
    val totalPages: Int,
    val totalElements: Long,
    val size: Int,
    val content: List<TbtiQuestionItem>,
    val number: Int,
    val sort: Sort?,
    val pageable: Pageable?,
    val numberOfElements: Int,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean
)

data class TbtiQuestionItem(
    val id: String,
    val content: String,
    val weight: Int,
    val traitCategory: String,
    val categoryIntial: String,
    val answers: List<TbtiAnswer>
)

data class TbtiAnswer(
        val content: String,
        val percentage: Int
)

data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)

data class Pageable(
    val offset: Long,
    val sort: Sort,
    val paged: Boolean,
    val pageNumber: Int,
    val pageSize: Int,
    val unpaged: Boolean
)