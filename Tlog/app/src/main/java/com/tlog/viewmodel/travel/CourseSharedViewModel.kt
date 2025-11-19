package com.tlog.viewmodel.travel

import androidx.lifecycle.ViewModel
import com.tlog.data.model.travel.AiRequest
import com.tlog.data.model.travel.AiTravel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class CourseSharedViewModel @Inject constructor() : ViewModel() {
    private val _aiRequest = MutableStateFlow<AiRequest?>(null)
    val aiRequest: StateFlow<AiRequest?> = _aiRequest.asStateFlow()

    private val _aiTravelMap = MutableStateFlow<Map<String, List<AiTravel>>>(emptyMap())
    val aiTravelMap: StateFlow<Map<String, List<AiTravel>>> = _aiTravelMap


    fun setAiTravelMap(map: Map<String, List<AiTravel>>) {
        _aiTravelMap.value = map
    }
    fun setAiRequest(request: AiRequest) {
        _aiRequest.value = request
    }

    fun getDayOfCount(): List<Int> {
        val ret = _aiRequest.value ?: return emptyList()

        return ret.dailyPlans.map { it.placeCount }
    }

    fun getStartDate(): String {
        val ret = _aiRequest.value ?: return ""

        return ret.dailyPlans.first().date
    }

    fun getEndDate(): String {
        val ret = _aiRequest.value ?: return ""

        return ret.dailyPlans.last().date
    }
}