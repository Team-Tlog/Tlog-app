package com.tlog.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tlog.data.model.TravelUiData


@Composable
fun TravelList(
    travelList: List<TravelUiData>,
    setCheckBox: (Int, Boolean) -> Unit,
) {
    LazyColumn {
        itemsIndexed(travelList) { index, item ->
            TravelItem(
                index = index,
                travelName = item.travelData.travelName,
                travelDescription = item.travelData.description,
                hashTags = item.travelData.hashTags,
                checked = item.checked,
                setCheckBox = setCheckBox
            )
            if (index == travelList.lastIndex) {
                Spacer(modifier = Modifier.height(75.dp)) // 마지막 아이템엔 더 큰 여백
            } else {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

    }
}