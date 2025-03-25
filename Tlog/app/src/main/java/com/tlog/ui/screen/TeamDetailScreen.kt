package com.tlog.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.MainButton
import com.tlog.ui.component.TeamHeader
import com.tlog.ui.component.TravelItem
import com.tlog.viewmodel.CartViewModel
import com.tlog.viewmodel.TeamDetailViewModel

@Preview(showBackground = true)
@Composable
fun TeamDetailScreen(
    cartViewModel: CartViewModel = viewModel(),
    teamDetailViewModel: TeamDetailViewModel = viewModel()
) {
    val team = teamDetailViewModel.teamData
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                TeamHeader(
                    team = team,
                    expanded = expanded,
                    onToggleExpand = { expanded = !expanded }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            itemsIndexed(cartViewModel.travelList.value) { index, item ->
                TravelItem(
                    index = index,
                    travelName = item.travelData.travelName,
                    travelDescription = item.travelData.description,
                    hashTags = item.travelData.hashTags,
                    checked = item.checked,
                    setCheckBox = { i, c -> cartViewModel.updateChecked(i, c) }
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 15.dp)
        ) {
            MainButton(
                text = "AI 코스 탐색 시작",
                onClick = { Log.d("AI", "탐색 시작") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )
        }
    }
}
