package com.tlog.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tlog.data.model.TravelData

class CartViewModel: ViewModel() {
//    private var _travelList = mutableStateOf<List<TravelData>>(emptyList())
    private var _travelList = mutableStateOf(listOf(
        TravelData(
            travelName = "남산타워",
            description = "남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요 남산타워는 커요",
            hashTags = listOf("크다", "높다")
        )
    ))
    val travelList = _travelList

    fun updateTravelList(newTravelList: List<TravelData>) {
        _travelList.value = newTravelList
    }

}