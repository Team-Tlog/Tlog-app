package com.tlog.ui.screen.share

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tlog.ui.component.mypage.BlueCircle
import com.tlog.ui.component.mypage.MyPageTbtiGroup
import com.tlog.ui.component.mypage.MyPageTopBar
import com.tlog.ui.component.mypage.SettingItem
import com.tlog.ui.component.mypage.UserInfoGroup
import com.tlog.ui.component.share.BottomBar
import com.tlog.viewmodel.share.MyPageViewModel


@Preview
@Composable
fun MyPageScreen(viewModel: MyPageViewModel = viewModel()) {
    val navController = rememberNavController()
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
                        MyPageTopBar()

                        Spacer(modifier = Modifier.height(24.dp))

                        MyPageTbtiGroup()

                        Spacer(modifier = Modifier.height(8.dp))

                        UserInfoGroup()
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