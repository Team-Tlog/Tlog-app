package com.tlog.ui.component.tmp

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tlog.data.model.travel.TravelUiData


@Composable
fun TmpTravelList(
    travelList: List<TravelUiData>,
    setCheckBox: (Int, Boolean) -> Unit,
    listState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        state = listState
    ) {
        itemsIndexed(travelList) { index, item ->
            TmpTravelItem(
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