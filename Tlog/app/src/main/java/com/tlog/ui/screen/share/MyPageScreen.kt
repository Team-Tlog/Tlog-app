package com.tlog.ui.screen.share

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.ui.component.mypage.BlueCircle
import com.tlog.ui.component.mypage.MyPageTbtiGroup
import com.tlog.ui.component.mypage.MyPageTopBar
import com.tlog.ui.component.mypage.SettingItem
import com.tlog.ui.component.mypage.UserInfoGroup
import com.tlog.ui.component.share.BottomBar
import com.tlog.viewmodel.share.MyPageViewModel
import com.tlog.viewmodel.share.MyPageViewModel.UiEvent


@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = hiltViewModel(),
    navController: NavController
) {

    val context = LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.LogoutSuccess -> {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true } // 모두 날려버려~
                    }
                }
                is UiEvent.LogoutError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is UiEvent.ProfileImageUpdated -> {
                    navController.popBackStack()
                    navController.navigate("myPage")
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 60.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp)
            ) {
                BlueCircle()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MyPageTopBar(
                            logoutClick = { viewModel.logout() }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        if (viewModel.isGetUserApiSuccess.value == true)
                            MyPageTbtiGroup(
                                userInfo = viewModel.userInfo.value!!,
                                tbtiTestClick = {
                                    navController.navigate("tbtiIntro")
                                }
                            )

                        Spacer(modifier = Modifier.height(8.dp))

                        val imagePickerLauncher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.GetContent()
                        ) { uri: Uri? ->
                            uri?.let {
                                viewModel.imageUri.value = it.toString()
                                viewModel.updateProfileImage(context)
                            }
                        }

                        if (viewModel.isGetUserApiSuccess.value == true)
                            UserInfoGroup(
                                userInfo = viewModel.userInfo.value!!,
                                onImageClick = {
                                    imagePickerLauncher.launch("image/*")
                                }
                            )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            SettingItem()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter)
        ) {
            BottomBar(
                navController = navController,
                selectedIndex = 3
            )
        }
    }
}