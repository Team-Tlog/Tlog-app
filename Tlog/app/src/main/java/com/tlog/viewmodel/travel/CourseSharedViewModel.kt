package com.tlog.viewmodel.travel

import androidx.lifecycle.ViewModel
import com.tlog.data.dto.request.travel.AiRequest
import com.tlog.data.dto.response.travel.AiTravel
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

    private val _selectedTravelNames = MutableStateFlow<List<String>>(emptyList())
    val selectedTravelNames = _selectedTravelNames.asStateFlow()


    // TEAM 공유 부분
    private val _teamId = MutableStateFlow<String>("")
    val teamId: StateFlow<String> = _teamId.asStateFlow()

    private val _city = MutableStateFlow<String>("")
    val city: StateFlow<String> = _city.asStateFlow()

    private val _district = MutableStateFlow<List<String>>(emptyList())
    val district: StateFlow<List<String>> = _district.asStateFlow()

    private val _startDate = MutableStateFlow<String>("")
    val startDate: StateFlow<String> = _startDate.asStateFlow()

    private val _endDate = MutableStateFlow<String>("")
    val endDate: StateFlow<String> = _endDate

    private val _hasPet = MutableStateFlow<Boolean>(false)
    val hasPet: StateFlow<Boolean> = _hasPet.asStateFlow()

    private val _hasTransport = MutableStateFlow<Boolean>(false)
    val hasTransport: StateFlow<Boolean> = _hasTransport.asStateFlow()

    private val _visitedCountPerDay = MutableStateFlow<Map<String, Int>>(emptyMap())
    val visitedCountPerDay: StateFlow<Map<String, Int>> = _visitedCountPerDay.asStateFlow()


    fun setTeamInfo(
        teamId: String,
        city: String,
        district: List<String>,
        startDate: String,
        endDate: String,
        hasPet: Boolean,
        hasTransport: Boolean,
        visitedCountPerDay: Map<String, Int>
    ) {
        _teamId.value = teamId
        _city.value = city
        _district.value = district
        _startDate.value = startDate
        _endDate.value = endDate
        _hasPet.value = hasPet
        _hasTransport.value = hasTransport
        _visitedCountPerDay.value = visitedCountPerDay
    }

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

    fun setSelectedTravelName(names: List<String>, onFinish: () -> Unit) {
        _selectedTravelNames.value = names

        onFinish()
    }
}
