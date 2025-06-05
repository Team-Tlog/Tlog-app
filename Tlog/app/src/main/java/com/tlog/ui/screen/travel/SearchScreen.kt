package com.tlog.ui.screen.travel

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.R
import com.tlog.ui.component.share.SearchBar
import com.tlog.ui.component.travel.PopularDestinations
import com.tlog.ui.component.travel.RecentSearches
import com.tlog.ui.component.travel.SearchTravelList
import com.tlog.ui.component.travel.TravelCategoryGrid
import com.tlog.viewmodel.share.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column (
            modifier =
                if (viewModel.searchResult.value.isEmpty() || !viewModel.checkSearchText()) {
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                }
                else
                    Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(42.dp))

            SearchBar(
                value = viewModel.searchText.collectAsState().value,
                onValueChange = {
                    viewModel.updateSearchText(it)
                    Log.d("SearchText", viewModel.searchText.value)
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "검색",
                        tint = Color(0xFF676767)
                    )
                }
            )

            Spacer(modifier = Modifier.height(26.dp))

            if (viewModel.searchResult.value.isEmpty() || !viewModel.checkSearchText()) {
                TravelCategoryGrid()

                Spacer(modifier = Modifier.height(25.dp))

                PopularDestinations()

                Spacer(modifier = Modifier.height(32.dp))

                RecentSearches()
            }
            else {
                SearchTravelList(
                    travelList = viewModel.searchResult.value,
                    onClick = { travelId, travelName ->
                        navController.navigate("travelInfo/${travelId}")
                    }
                )
            }
        }
    }
}