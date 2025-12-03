package com.tlog.ui.screen.share

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tlog.ui.component.travel.TravelCard
import com.tlog.ui.style.BodyTitle
import com.tlog.viewmodel.base.BaseViewModel.UiEvent
import com.tlog.viewmodel.share.BannerViewModel

@Composable
fun BannerDetailScreen(
    bannerId: String,
    viewModel: BannerViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current

    val destinations by viewModel.destinations.collectAsState()
    val title by viewModel.title.collectAsState()
    val scraps by viewModel.scraps.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getBannerDetail(bannerId)

        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.target) {
                        if (event.clearBackStack) { popUpTo(navController.graph.id) { inclusive = true } }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is UiEvent.PopBackStack -> Unit
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        item {
            Text(
                text = title,
                style = BodyTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(33.dp))
        }

        destinations.forEach { destination ->
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, bottom = 16.dp),
                ) {
                    TravelCard(
                        travel = destination,
                        isFavorite = { scraps.contains(destination.travelId) },
                        onFavoriteToggle = { viewModel.toggleScrap(destination.travelId) },
                        onClick = { viewModel.navToTravelInfo(destination.travelId) }
                    )
                }
            }
        }
    }
}
