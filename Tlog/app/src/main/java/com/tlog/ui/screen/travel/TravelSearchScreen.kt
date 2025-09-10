package com.tlog.ui.screen.travel

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.R
import com.tlog.ui.component.share.SearchBar
import com.tlog.ui.component.travel.PopularDestinations
import com.tlog.ui.component.travel.RecentSearches
import com.tlog.ui.component.travel.SearchTravelItem
import com.tlog.ui.component.travel.TravelCategoryGrid
import com.tlog.viewmodel.share.SearchViewModel
import com.tlog.viewmodel.share.SearchViewModel.UiEvent

@Composable
fun TravelSearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.target) {
                        if (event.clearBackStack) popUpTo(navController.graph.id) { inclusive = true }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn (
            modifier = Modifier.fillMaxSize()
        ) {
            item {
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
            }

            if (viewModel.searchResult.value.isEmpty() || !viewModel.checkSearchText()) {
               item {
                   TravelCategoryGrid()

                   Spacer(modifier = Modifier.height(25.dp))

                   PopularDestinations()

                   Spacer(modifier = Modifier.height(32.dp))

                   RecentSearches()
               }
            }
            else {
                itemsIndexed(viewModel.searchResult.value) { index, item ->
                    SearchTravelItem(travel = item, onClick = { travelId, _ ->
//                        navController.navigate("travelInfo/${travelId}")
                        viewModel.navToTravelInfo(travelId)
                    })
                    if (index == viewModel.searchResult.value.lastIndex) {
                        Spacer(modifier = Modifier.height(75.dp)) // 마지막 아이템엔 더 큰 여백
                    } else {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}