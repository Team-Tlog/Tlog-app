package com.tlog.data.api

import com.tlog.data.model.tbti.TbtiQuestion

data class TbtiQuestionResponse(
    val totalPages: Int,
    val totalElements: Long,
    val size: Int,
    val content: List<TbtiQuestion>,
    val number: Int,
    val sort: Sort?,
    val pageable: Pageable?,
    val numberOfElements: Int,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean
)

data class TbtiDescriptionResponse(
    val tbtiString: String,
    val imageUrl: String,
    val secondName: String,
    val description: String,
)
