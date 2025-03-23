package com.tlog.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class UserInfoViewModel: ViewModel() {

    private var _nickname = mutableStateOf("")
    val nickname = _nickname
    private var _gender = mutableStateOf("성별")
    val gender = _gender
    private var _hasPet = mutableStateOf(false)
    val hasPet = _hasPet
    private var _travelType = mutableStateOf("가족여행")
    val travelType = _travelType
    private var _hasCar = mutableStateOf(false)
    val hasCar = _hasCar


    fun updateNickname(newName: String) {
        _nickname.value = newName
    }

    fun updateGender(newGender: String) {
        _gender.value = newGender
    }

    fun updateCar(newHasCar: Boolean) {
        _hasCar.value = newHasCar
    }

    fun updateTravelType(newTravelType: String) {
        _travelType.value = newTravelType
    }

    fun updatePet(newHasPet: Boolean) {
        _hasPet.value = newHasPet
    }
}
