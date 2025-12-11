package com.tlog.domain.mapper

import com.tlog.data.dto.tbti.TbtiAnswerDto
import com.tlog.data.dto.tbti.TbtiDescriptionDto
import com.tlog.data.dto.tbti.TbtiQuestionDto
import com.tlog.domain.model.tbti.TbtiAnswer
import com.tlog.domain.model.tbti.TbtiDescription
import com.tlog.domain.model.tbti.TbtiQuestion

fun TbtiDescriptionDto.toDomain(): TbtiDescription {
    return TbtiDescription(
        tbtiString = tbtiString,
        imageUrl = imageUrl ?: "",
        secondName = secondName,
        description = description,
        preferredTbti = preferredTbti,
        notPreferredTbti = notPreferredTbti
    )
}

fun TbtiAnswerDto.toDomain(): TbtiAnswer {
    return TbtiAnswer(
        content = content,
        percentage = percentage
    )
}

fun TbtiQuestionDto.toDomain(): TbtiQuestion {
    return TbtiQuestion(
        id = id,
        content = content,
        weight = weight,
        traitCategory = traitCategory,
        categoryIntial = categoryIntial,
        answers = answers.map { it.toDomain() }
    )
}
