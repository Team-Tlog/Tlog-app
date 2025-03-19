package com.tlog.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class UserInfoViewModel: ViewModel() {

    var nickname by mutableStateOf("")
    var gender by mutableStateOf("없음")
    var hasPet by mutableStateOf(false)
    var travelType by mutableStateOf("가족여행")
    var hasCar by mutableStateOf(false)


    fun updateNickname(newName: String) {
        nickname = newName
    }

    fun updateGender(newGender: String) {
        gender = newGender
    }

    fun updateCar(newHasCar: Boolean) {
        hasCar = newHasCar
    }

    fun updateTravelType(newTravelType: String) {
        travelType = newTravelType
    }

    fun updatePet(newHasPet: Boolean) {
        hasPet = newHasPet
    }
}
