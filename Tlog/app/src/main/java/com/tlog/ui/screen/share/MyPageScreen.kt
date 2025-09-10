package com.tlog.ui.screen.share

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.R
import com.tlog.ui.component.mypage.BlueCircle
import com.tlog.ui.component.mypage.MyPageTbtiGroup
import com.tlog.ui.component.mypage.MyPageTopBar
import com.tlog.ui.component.mypage.UserInfoGroup
import com.tlog.ui.component.share.BottomBar
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.share.MyPageViewModel
import com.tlog.viewmodel.share.MyPageViewModel.UiEvent


@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = hiltViewModel(),
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
                is UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
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
            viewModel.getUserInfo.collectAsState().let { isGetUserApiSuccess ->

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 13.dp)
                        .padding(
                            top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                        )
                ) {
                    BlueCircle()

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        MyPageTopBar(
                            logoutClick = { viewModel.logout() }
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Spacer(modifier = Modifier.height(25.dp))

                            if (isGetUserApiSuccess.value && viewModel.userInfo.value != null)
                                MyPageTbtiGroup(
                                    userInfo = viewModel.userInfo.value!!,
                                    tbtiTestClick = {
                                        viewModel.navToTbtiTest()
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

                            if (isGetUserApiSuccess.value && viewModel.userInfo.value != null)
                                UserInfoGroup(
                                    userInfo = viewModel.userInfo.value!!,
                                    onImageClick = {
                                        imagePickerLauncher.launch("image/*")
                                    }
                                )
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "알림 설정",
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(10.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .height(21.dp)
                            .width(42.dp)
                    ) {
                        Switch(
                            checked = viewModel.notification.value,
                            onCheckedChange = { viewModel.changeNotification() },
                            colors = SwitchDefaults.colors(
                                uncheckedTrackColor = Color(0xFFE1E1E1),
                                uncheckedThumbColor = Color.White,
                                checkedThumbColor = Color.White,
                                checkedTrackColor = MainColor,
                                uncheckedBorderColor = Color.Transparent
                            ),
                            modifier = Modifier // 고정된 크기라 scale로 비율 조정
                                .scale(0.656f)
                        )
                    }
                }

                val tmpList = listOf("개인정보처리방침", "고객센터", "개발자에게 피드백 해주기")

                tmpList.forEach { text ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                when (text) {
                                    "개인정보처리방침" -> {
                                        Log.d(text, "my click!!")
                                    }

                                    "고객센터" -> {
                                        Log.d(text, "my click!!")
                                    }

                                    "개발자에게 피드백 해주기" -> {
                                        Log.d(text, "my click!!")
                                    }
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = text,
                            fontFamily = MainFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(10.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_right),
                            contentDescription = text,
                            tint = Color.Black
                        )
                    }
                }

                Text(
                    text = "회원탈퇴",
                    fontFamily = MainFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
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