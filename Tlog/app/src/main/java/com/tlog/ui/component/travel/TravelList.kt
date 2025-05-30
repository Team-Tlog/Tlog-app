package com.tlog.ui.component.travel

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tlog.data.model.travel.SearchTravel
import com.tlog.data.model.travel.Travel


@Composable
fun TravelList(
    travelList: List<Travel>,
    listState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        state = listState
    ) {
        itemsIndexed(travelList) { index, item ->
            TravelItem(travel = item)
            if (index == travelList.lastIndex) {
                Spacer(modifier = Modifier.height(75.dp)) // 마지막 아이템엔 더 큰 여백
            } else {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

    }
}


@Composable
fun SearchTravelList(
    travelList: List<SearchTravel>,
    onClick: (String, String) -> Unit,
    listState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        state = listState
    ) {
        itemsIndexed(travelList) { index, item ->
            SearchTravelItem(travel = item, onClick = onClick)
            if (index == travelList.lastIndex) {
                Spacer(modifier = Modifier.height(75.dp)) // 마지막 아이템엔 더 큰 여백
            } else {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

    }
}