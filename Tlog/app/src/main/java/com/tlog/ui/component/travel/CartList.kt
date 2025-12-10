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
import com.tlog.domain.model.travel.ViewTravel

@Composable
fun CartList(
    travelList: List<ViewTravel>,
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
