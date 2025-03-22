package com.tlog.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.tlog.data.model.TravelData


@Composable
fun TravelList(
    travelList: List<TravelData>,
    checked: Boolean = false
) {
    LazyColumn {
        items(travelList) { item ->
            TravelItem(
                travelName = item.travelName,
                travelDescription = item.description,
                hashTags = item.hashTags,
                checked =  checked
            )
        }
    }
}