package com.tlog.data.repository

import com.tlog.api.AiApi
import com.tlog.api.SnsApi
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.response.course.CourseDto
import com.tlog.data.dto.request.sns.PostWriteBody
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.sns.SnsPost
import javax.inject.Inject

class SnsPostRepository@Inject constructor (
    private val aiApi: AiApi,
    private val snsApi: SnsApi
) {
    // 삭제 예정 (딱히 모델 작업 x)
    suspend fun getCourses(userId: String): BaseResponse<List<CourseDto>> {
        return aiApi.getUserCourses(userId)
    }

    suspend fun postWrite(userId: String, courseId: String, content: String, imageUrls: List<String>): SnsPost {
        return snsApi.createPost(PostWriteBody(userId, courseId, content, imageUrls)).data.toDomain()
    }
}
