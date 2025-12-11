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
fun ScrapTravelList(
    scrapTravelList: List<ViewTravel>,
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
