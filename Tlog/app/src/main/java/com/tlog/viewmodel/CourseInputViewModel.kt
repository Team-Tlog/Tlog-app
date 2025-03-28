package com.tlog.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CourseInputViewModel: ViewModel() {
    private var _city = mutableStateOf("지역")
    val city = _city
    private var _hasPet = mutableStateOf(false)
    val hasPet = _hasPet
    private var _hasCar = mutableStateOf(false)
    val hasCar = _hasCar

    fun updateCity(newCity: String) {
        _city.value = newCity
    }

    fun updateCar(newHasCar: Boolean) {
        _hasCar.value = newHasCar
    }

    fun updatePet(newHasPet: Boolean) {
        _hasPet.value = newHasPet
    }
}