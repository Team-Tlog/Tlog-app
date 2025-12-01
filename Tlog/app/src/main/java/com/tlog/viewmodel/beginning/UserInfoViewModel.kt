package com.tlog.viewmodel.beginning

import com.tlog.viewmodel.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class UserInfoViewModel: BaseViewModel() {

    private var _nickname = MutableStateFlow("")
    val nickname = _nickname.asStateFlow()
    private var _gender = MutableStateFlow("성별")
    val gender = _gender.asStateFlow()
    private var _hasPet = MutableStateFlow(false)
    val hasPet = _hasPet.asStateFlow()
    private var _travelType = MutableStateFlow("가족여행")
    val travelType = _travelType.asStateFlow()
    private var _hasCar = MutableStateFlow(false)
    val hasCar = _hasCar.asStateFlow()


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
