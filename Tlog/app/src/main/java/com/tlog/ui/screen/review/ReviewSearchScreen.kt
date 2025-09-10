package com.tlog.ui.screen.review

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tlog.R
import com.tlog.ui.component.share.SearchBar
import com.tlog.ui.component.share.TopBar
import com.tlog.ui.component.travel.SearchTravelList
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.share.SearchViewModel
import com.tlog.viewmodel.share.SearchViewModel.UiEvent


@Composable
fun ReviewSearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController
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

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TopBar(
                text = "리뷰작성"
            )

            Spacer(modifier = Modifier.height(10.dp))

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


            if (viewModel.searchResult.value.isEmpty() || !viewModel.checkSearchText()) {
                Spacer(modifier = Modifier.height(201.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable {
                                viewModel.navToAddTravel()
                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = "여행지 등록",
                            modifier = Modifier
                                .size(64.dp)
                                .padding(bottom = 15.dp)
                        )
                        Text(
                            text = "여행지 등록하기",
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontFamily = MainFont,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(20.dp))

                SearchTravelList(
                    travelList = viewModel.searchResult.value,
                    onClick = { travelId, travelName ->
                        viewModel.navToReviewWrite(travelId, travelName)
                    }
                )
            }
        }
    }


}