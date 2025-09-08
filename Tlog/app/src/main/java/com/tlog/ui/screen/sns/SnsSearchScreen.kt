package com.tlog.ui.screen.sns

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.tlog.ui.component.travel.RecentSearches
import com.tlog.viewmodel.sns.SnsSearchViewModel


@Composable
fun SnsSearchScreen(
    viewModel: SnsSearchViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                is SnsSearchViewModel.UiEvent.Error -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column (
            modifier = Modifier.fillMaxSize()
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
                    RecentSearches()
            }
            else {
                    PostsGrid(
                        postList = viewModel.searchResult.value,
                        onClick = { postId ->
                            navController.navigate("snsPostDetail/$postId")
                        }
                    )
            }
        }
    }
}