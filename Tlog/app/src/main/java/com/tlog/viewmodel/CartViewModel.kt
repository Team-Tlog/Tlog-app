package com.tlog.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tlog.data.model.TravelData
import com.tlog.data.model.TravelUiData

class CartViewModel: ViewModel() {
//    private var _travelList = mutableStateOf<List<TravelData>>(emptyList())
    // 추후 api로 리스트 설정 (현재 리스트 삭제)
    private var _travelList = mutableStateOf(listOf(
        TravelUiData(
            TravelData(
                travelName = "남산타워",
                description = "남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요",
                hashTags = listOf("크다", "높다")
            ),
            checked = false
        ),
        TravelUiData(
            TravelData(
                travelName = "남산타워",
                description = "남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요",
                hashTags = listOf("크다", "높다")
            ),
            checked = false
        ),
        TravelUiData(
            TravelData(
                travelName = "남산타워",
                description = "남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요",
                hashTags = listOf("크다", "높다")
            ),
            checked = false
        )
    ))
    val travelList = _travelList
    private var _allChecked = mutableStateOf(false)


    fun updateTravelList(newTravelList: List<TravelUiData>) {
        _travelList.value = newTravelList
    }

    fun updateChecked(index: Int, newChecked: Boolean) {
        val updateList = _travelList.value.toMutableList()
        val currentItem = updateList[index]

        updateList[index] = currentItem.copy(checked = newChecked)
        _travelList.value = updateList
    }

    fun allChecked() {
        _allChecked.value = !(_allChecked.value)
        _travelList.value = _travelList.value.map {
            it.copy(checked = _allChecked.value)
        }
    }

}