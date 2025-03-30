package com.tlog.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class CourseInputViewModel: ViewModel() {
    private val _city = mutableStateOf("지역")
    val city: State<String> = _city

    private val _hasPet = mutableStateOf(false)
    val hasPet: State<Boolean> = _hasPet

    private val _hasCar = mutableStateOf(false)
    val hasCar: State<Boolean> = _hasCar

    private val _startDate = mutableStateOf<LocalDate?>(null)
    val startDate: State<LocalDate?> = _startDate

    private val _endDate = mutableStateOf<LocalDate?>(null)
    val endDate: State<LocalDate?> = _endDate

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

    fun clearDateRange() {
        _startDate.value = null
        _endDate.value = null
    }
}