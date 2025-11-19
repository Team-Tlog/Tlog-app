package com.tlog.viewmodel.travel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.local.RegionCode
import com.tlog.data.model.travel.AiRequest
import com.tlog.data.model.travel.DailyPlan
import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CourseInputViewModel @Inject constructor(
    private val tokenProvider: TokenProvider
): BaseViewModel() {
    private val _city = mutableStateOf("지역")
    val city: State<String> = _city

    private val _district = mutableStateOf("지역을 선택해주세요")
    val district: State<String> = _district

    private val _checkedDistrict = mutableStateOf<Set<String>>(emptySet())
    val checkedDistrict: State<Set<String>> = _checkedDistrict

    private val _hasPet = mutableStateOf(false)
    val hasPet: State<Boolean> = _hasPet

    private val _hasCar = mutableStateOf(false)
    val hasCar: State<Boolean> = _hasCar

    private val _startDate = mutableStateOf<LocalDate?>(null)
    val startDate: State<LocalDate?> = _startDate

    private val _endDate = mutableStateOf<LocalDate?>(null)
    val endDate: State<LocalDate?> = _endDate

    private val _travelCountByDate = mutableStateOf<Map<LocalDate, Int>>(emptyMap())
    val travelCountByDate: State<Map<LocalDate, Int>> = _travelCountByDate




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

    fun navToAiCourseSelectCart() {
        navigate(Screen.AiCourseSelectCart)
    }

}
