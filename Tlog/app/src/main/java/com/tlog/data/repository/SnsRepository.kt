package com.tlog.data.repository

import com.tlog.api.SnsApi
import com.tlog.api.SnsPost
import com.tlog.api.UpdateSnsIdRequest
import com.tlog.data.api.BaseListResponse
import com.tlog.data.api.BaseResponse
import javax.inject.Inject

class SnsRepository @Inject constructor(
    private val retrofitInstance: SnsApi,
) {
    suspend fun updateSnsId(snsId: String): BaseResponse<Unit> {
        return retrofitInstance.updateSnsId(UpdateSnsIdRequest(snsId))
    }

    suspend fun getFollowingPostList(
        lastPostId: String? = null,
        size: Int,
    ) :BaseListResponse<List<SnsPost>> {
        return retrofitInstance.getFollowingPostList(lastPostId, size)
    }
}