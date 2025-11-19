package com.tlog.viewmodel.travel

import com.tlog.api.AiTravel
import com.tlog.data.repository.AiRecommendCourseResultRepository
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


@HiltViewModel
class AiRecommendCourseResultViewModel @Inject constructor(
    val repository: AiRecommendCourseResultRepository
) : BaseViewModel() {
    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    private val _aiTravelMap = MutableStateFlow<Map<String, List<AiTravel>>>(emptyMap())
    val aiTravelMap: StateFlow<Map<String, List<AiTravel>>> = _aiTravelMap

    private val _dayOfCount = MutableStateFlow<List<Int>>(emptyList())
    val dayOfCount: StateFlow<List<Int>> = _dayOfCount.asStateFlow()

    private val _dailyTravels = MutableStateFlow<List<List<AiTravel>>>(emptyList())
    val dailyTravels: StateFlow<List<List<AiTravel>>> = _dailyTravels.asStateFlow()

    private val _uiTravels = MutableStateFlow<List<AiTravel>>(emptyList())
    val uiTravels: StateFlow<List<AiTravel>> = _uiTravels.asStateFlow()




    fun updateSelectedDay(idx: Int) {
        _selectedDay.value = idx

        updateUiTravels()
    }

    fun setAiTravelMap(map: Map<String, List<AiTravel>>, dayOfCount: List<Int>) {
        _aiTravelMap.value = map
        _dayOfCount.value = dayOfCount

        val flatList = map.values.flatten()

        val split = mutableListOf<List<AiTravel>>()
        var index = 0

        dayOfCount.forEach { count ->
            val end = index + count
            if (index < flatList.size) {
                split.add(flatList.subList(index, minOf(end, flatList.size)))
            } else {
                split.add(emptyList())
            }
            index = end
        }

        _dailyTravels.value = split

        updateUiTravels()
    }

    fun deleteTravelByName(name: String) {
        val updatedUiList = _uiTravels.value.filter { it.name != name }
        _uiTravels.value = updatedUiList

        val dayIndex = _selectedDay.value - 1
        if (dayIndex in _dailyTravels.value.indices) {
            val updatedDailyList = _dailyTravels.value.toMutableList()
            updatedDailyList[dayIndex] = updatedUiList
            _dailyTravels.value = updatedDailyList
        }
    }

    fun getDayCount(): Int {
        return _dayOfCount.value.size
    }

    private fun updateUiTravels() {
        val dayIndex = _selectedDay.value - 1

        if (dayIndex in _dailyTravels.value.indices) {
            _uiTravels.value = _dailyTravels.value[dayIndex]
        } else {
            _uiTravels.value = emptyList()
        }
    }
}
