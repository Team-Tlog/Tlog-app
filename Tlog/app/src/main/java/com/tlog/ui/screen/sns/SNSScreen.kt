package com.tlog.ui.screen.sns

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.R
import com.tlog.ui.component.SNS.PostItem
import com.tlog.ui.component.share.BottomBar
import com.tlog.ui.component.share.MainTopBar
import com.tlog.viewmodel.sns.SnsViewModel


@Composable
fun SNSScreen(
    viewModel: SnsViewModel = hiltViewModel(),
    navController: NavController
) {
    Scaffold(
        topBar = {
            MainTopBar(
                searchIconClickable = { navController.navigate("search") },
                notificationIconClickable = { navController.navigate("notification") }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        60.dp + WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding()
                    )
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                BottomBar(
                    navController = navController,
                    selectedIndex = 0
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                items(viewModel.postList.value) { post ->
                    PostItem(post = post, viewModel = viewModel)

                }
            }
        }
    }

}