package com.tlog.ui.screen.travel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tlog.ui.component.share.SearchBar
import com.tlog.ui.component.travel.PopularDestinations
import com.tlog.ui.component.travel.RecentSearches
import com.tlog.ui.component.travel.TravelCategoryGrid

@Composable
fun SearchScreen() {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(
            value = "",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(24.dp))
        TravelCategoryGrid()
        Spacer(modifier = Modifier.height(24.dp))
        PopularDestinations(
            destinations = listOf(1, 2, 3)
        )
        Spacer(modifier = Modifier.height(24.dp))
        RecentSearches(
            recentKeywords = listOf("서울", "부산", "제주", "강릉", "인천")
        )
    }
}