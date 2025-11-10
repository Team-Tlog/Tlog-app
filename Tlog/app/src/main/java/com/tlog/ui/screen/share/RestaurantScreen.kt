package com.tlog.ui.screen.share

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tlog.ui.component.share.RestaurantItem
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.base.BaseViewModel.UiEvent
import com.tlog.viewmodel.share.RestaurantViewModel


@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun RestaurantScreen(
    latitude: Double = 37.5665,
    longitude: Double = 126.9780,
    viewModel: RestaurantViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> Unit
                is UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is UiEvent.PopBackStack -> Unit
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getRestaurants(latitude, longitude)
        viewModel.getCafes(latitude, longitude)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .drawBehind {
                        val strokeWidth = 2.dp.toPx()
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            color = if (viewModel.selectedTab.value == "식당") MainColor else Color.Transparent,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
                    .clickable {
                        if (viewModel.selectedTab.value != "식당") {
                            viewModel.updateSelectedTab("식당")
                            viewModel
                        }
                    }
                    .weight(1f)
            ) {
                Text(
                    text = "식당",
                    fontFamily = MainFont,
                    fontSize = 18.sp,
                    fontWeight = if (viewModel.selectedTab.value == "식당") FontWeight.Bold else FontWeight.Medium,
                    color = if (viewModel.selectedTab.value == "식당") MainColor else Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = (11.5).dp)
                        .fillMaxWidth()
                )
            }

            Box(
                modifier = Modifier
                    .drawBehind {
                        val strokeWidth = 2.dp.toPx()
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            color = if (viewModel.selectedTab.value == "카페") MainColor else Color.Transparent,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
                    .clickable {
                        if (viewModel.selectedTab.value != "카페") {
                            viewModel.updateSelectedTab("카페")
                            viewModel
                        }
                    }
                    .weight(1f)
            ) {
                Text(
                    text = "카페",
                    fontSize = 18.sp,
                    fontFamily = MainFont,
                    fontWeight = if (viewModel.selectedTab.value == "카페") FontWeight.Bold else FontWeight.Medium,
                    color = if (viewModel.selectedTab.value == "카페") MainColor else Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = (11.5).dp)
                        .fillMaxWidth()
                )
            }
        }

        val restaurants = if (viewModel.selectedTab.value == "식당")
            viewModel.restaurants.collectAsState().value
        else
            viewModel.cafes.collectAsState().value

        LazyColumn {
            itemsIndexed(
                items = restaurants,
                key = { _, item -> item.placeName }
            ) { index, item ->
                Log.d("DEBUG_ITEM", "restaurant=$item")
                RestaurantItem(
                    restaurant = item,
                    context = context
                )

                if (index == restaurants.lastIndex) {
                    Spacer(modifier = Modifier.height(75.dp)) // 마지막 아이템엔 더 큰 여백
                } else {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

        }
    }
}
