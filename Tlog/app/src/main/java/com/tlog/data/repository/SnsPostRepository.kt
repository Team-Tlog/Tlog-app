package com.tlog.data.repository

import com.tlog.api.AiApi
import com.tlog.api.SnsApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.CourseItem
import com.tlog.data.api.PostWriteBody
import com.tlog.data.api.SnsPost
import javax.inject.Inject

class SnsPostRepository@Inject constructor (
    private val aiApi: AiApi,
    private val snsApi: SnsApi
) {
    suspend fun getCourses(userId: String): BaseResponse<List<CourseItem>> {
        return aiApi.getUserCourses(userId)
    }

    suspend fun postWrite(userId: String, courseId: String, content: String, imageUrls: List<String>): BaseResponse<SnsPost> {
        return snsApi.createPost(PostWriteBody(userId, courseId, content, imageUrls))
    }


}