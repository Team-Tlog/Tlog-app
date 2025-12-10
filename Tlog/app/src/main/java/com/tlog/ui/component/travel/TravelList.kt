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
import com.tlog.data.dto.response.travel.TravelSearch
import com.tlog.data.dto.travel.CartDto
import com.tlog.data.dto.travel.TravelDto
import com.tlog.domain.model.travel.CartTravel
import com.tlog.domain.model.travel.ScrapTravel


@Composable
fun TravelList(
    travelList: List<TravelDto>,
    listState: LazyListState = rememberLazyListState(),
    onClick: (String) -> Unit,
    isChecked: (String) -> Boolean
) {
    LazyColumn(
        state = listState
    ) {
        itemsIndexed(
            items = travelList,
            key = { _, travel -> travel.name }
        ) { index, item ->
            TravelItem(
                travel = item,
                onClick = onClick,
                isChecked = isChecked
            )
            if (index == travelList.lastIndex) {
                Spacer(modifier = Modifier.height(75.dp)) // 마지막 아이템엔 더 큰 여백
            } else {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

    }
}

@Composable
fun CheckCartList(
    travelList: List<CartDto>,
    listState: LazyListState = rememberLazyListState(),
    onClick: (String) -> Unit,
    isChecked: (String) -> Boolean
) {
    LazyColumn(
        state = listState
    ) {
        itemsIndexed(
            items = travelList,
            key = { _, travel -> travel.name }
        ) { index, item ->
            CheckedCartItem(
                travel = item,
                onClick = onClick,
                isChecked = isChecked
            )
            if (index == travelList.lastIndex) {
                Spacer(modifier = Modifier.height(75.dp)) // 마지막 아이템엔 더 큰 여백
            } else {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

    }
}

@Composable
fun CartList(
    travelList: List<CartTravel>,
    listState: LazyListState = rememberLazyListState(),
    onClick: (String) -> Unit,
    getIsChecked: (String) -> Boolean,
    onCheckedClick: (String) -> Unit
) {
    LazyColumn(
        state = listState
    ) {
        itemsIndexed(
            items = travelList,
            key = { _, travel -> travel.name }
        ) { index, item ->
            CartItem(
                travel = item,
                onClick = onClick,
                getIsChecked = getIsChecked,
                onCheckedClick = onCheckedClick
            )
            if (index == travelList.lastIndex) {
                Spacer(modifier = Modifier.height(75.dp)) // 마지막 아이템엔 더 큰 여백
            } else {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

    }
}

@Composable
fun ScrapTravelList(
    scrapTravelList: List<ScrapTravel>,
    listState: LazyListState = rememberLazyListState(),
    onClick: (String) -> Unit,
    getIsChecked: (String) -> Boolean,
    onCheckedClick: (String) -> Unit
) {
    LazyColumn(
        state = listState
    ) {
        itemsIndexed(
            items = scrapTravelList,
            key = { _, scrap -> scrap.name }
        ) { index, item ->
            ScrapTravelItem(
                travel = item,
                onClick = onClick,
                getIsChecked = getIsChecked,
                checkedClick = onCheckedClick
            )
            if (index == scrapTravelList.lastIndex) {
                Spacer(modifier = Modifier.height(75.dp)) // 마지막 아이템엔 더 큰 여백
            } else {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

    }
}


@Composable
fun SearchTravelList(
    travelList: List<TravelSearch>,
    onClick: (String, String) -> Unit,
    listState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        state = listState
    ) {
        itemsIndexed(
            items = travelList,
            key = { _, travel -> travel.name }
        ) { index, item ->
            SearchTravelItem(travel = item, onClick = onClick)
            if (index == travelList.lastIndex) {
                Spacer(modifier = Modifier.height(75.dp)) // 마지막 아이템엔 더 큰 여백
            } else {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

    }
}
