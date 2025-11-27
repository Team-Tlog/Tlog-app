package com.tlog.viewmodel.travel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.tlog.data.local.TokenProvider
import com.tlog.data.local.RegionCode
import com.tlog.data.model.request.travel.AiRequest
import com.tlog.data.model.request.travel.DailyPlan
import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CourseInputViewModel @Inject constructor(): BaseViewModel() {
    private val _city = MutableStateFlow("지역")
    val city = _city.asStateFlow()

    private val _district = MutableStateFlow("지역을 선택해주세요")
    val district = _district.asStateFlow()

    private val _checkedDistrict = MutableStateFlow<Set<String>>(emptySet())
    val checkedDistrict = _checkedDistrict.asStateFlow()

    private val _hasPet = MutableStateFlow(false)
    val hasPet = _hasPet.asStateFlow()

    private val _hasCar = MutableStateFlow(false)
    val hasCar = _hasCar.asStateFlow()

    private val _startDate = MutableStateFlow<LocalDate?>(null)
    val startDate = _startDate.asStateFlow()

    private val _endDate = MutableStateFlow<LocalDate?>(null)
    val endDate = _endDate.asStateFlow()

    private val _travelCountByDate = MutableStateFlow<Map<LocalDate, Int>>(emptyMap())
    val travelCountByDate = _travelCountByDate.asStateFlow()

    fun updateCheckedDistrict(district: String) {
        _checkedDistrict.value = _checkedDistrict.value + district
    }

    fun deleteCheckedDistrict(district: String) {
        _checkedDistrict.value = _checkedDistrict.value - district
    }

    fun updateCity(newCity: String) {
        _city.value = newCity
    }

    fun updateCar(newHasCar: Boolean) {
        _hasCar.value = newHasCar
    }

    fun updatePet(newHasPet: Boolean) {
        _hasPet.value = newHasPet
    }

    fun updateDateRange(clickedDate: LocalDate) {
        if (_startDate.value == null || _endDate.value != null) {
            _startDate.value = clickedDate
            _endDate.value = null
        } else {
            if (clickedDate.isBefore(_startDate.value)) {
                _endDate.value = _startDate.value
                _startDate.value = clickedDate
            } else
                _endDate.value = clickedDate
        }
    }

    fun getTravelDates(): List<LocalDate> {
        val start = startDate.value
        val end = endDate.value

        return if (start != null && end != null) {
            generateSequence(start) { it.plusDays(1) }
                .takeWhile { !it.isAfter(end) }
                .toList()
        } else emptyList()
    }

    fun updatePlaceCount(date: LocalDate, count: Int) {
        _travelCountByDate.value = _travelCountByDate.value.toMutableMap().apply {
            this[date] = count
        }
    }

    fun getAiRequest(): AiRequest {
        val regionList = checkedDistrict.value.map {
            RegionCode.fromStringOrNull(it)
        }

        val dailyPlan = _travelCountByDate.value.map {
            val date = it.key
            val placeCount = it.value

            DailyPlan(
                date = date.toString(),
                placeCount = placeCount
            )
        }

        return AiRequest(
            city = city.value,
            region_codes = regionList,
            dailyPlans = dailyPlan,
            wishlist = emptyList()
        )
    }

    fun navToAiCourseSelectCart(isTeam: Boolean) {
        navigate(Screen.AiCourseSelectCart(isTeam))
    }

    fun setSelectedInfo(
        city: String,
        district: List<String>,
        startDate: String,
        endDate: String,
        hasPet: Boolean,
        hasTransport: Boolean,
        visitedCountPerDay: Map<String, Int>
    ) {
        val travelCountByDate = visitedCountPerDay.toSortedMap().map {
            LocalDate.parse(startDate).plusDays(it.key.toLong() - 1L) to it.value
        }.toMap()


        _city.value = city
        _startDate.value = LocalDate.parse(startDate)
        _endDate.value = LocalDate.parse(endDate)
        _hasPet.value = hasPet
        _hasCar.value = hasTransport
        _travelCountByDate.value = travelCountByDate
        _checkedDistrict.value = district.map { RegionCode.toRegionNameOrNull(it.toInt()) }.toSet()


    }
}
