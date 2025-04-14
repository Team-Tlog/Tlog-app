package com.tlog.data.model.travel

import com.tlog.data.model.tmp.TmpTravelData

data class TravelUiData(
    val travelData: TmpTravelData,
    val checked: Boolean = false
)
