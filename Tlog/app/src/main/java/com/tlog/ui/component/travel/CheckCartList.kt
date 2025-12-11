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
import com.tlog.data.dto.travel.CartDto

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
