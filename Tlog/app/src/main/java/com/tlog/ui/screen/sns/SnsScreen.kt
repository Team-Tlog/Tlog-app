package com.tlog.ui.screen.sns

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.ui.component.sns.PostItem
import com.tlog.ui.component.share.BottomBar
import com.tlog.ui.component.share.MainTopBar
import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.sns.SnsViewModel
import com.tlog.viewmodel.sns.SnsViewModel.UiEvent


@Composable
fun SnsScreen(
    viewModel: SnsViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.target) {
                        if (event.clearBackStack) popUpTo(Screen.Main) { inclusive = false }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
                is UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


    val followingList = viewModel.followingList.collectAsState().value
    val postList = viewModel.postList.collectAsState().value

    Scaffold(
        topBar = {
            MainTopBar(
                searchIconClickable = { navController.navigate("snsSearch") },
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
                    selectedIndex = 2
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            postList.let { postList ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    items(postList) { post ->
                        PostItem(
                            post = post,
                            isFollowing = followingList.contains(post.authorId),
                            clickUser = { userId ->
                                viewModel.navToSnsMyPage(userId)
                            },
                            courseClick = { postId ->
                                viewModel.navToSnsPostDetail(postId)
                            },
                            followClick = {
                                viewModel.followUser(post.authorId)
                            }
                        )

                    }
                }
            }
        }
    }

}