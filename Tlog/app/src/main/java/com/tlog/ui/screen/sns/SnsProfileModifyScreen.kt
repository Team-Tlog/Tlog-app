package com.tlog.ui.screen.sns

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.ui.component.sns.EditableTextField
import com.tlog.ui.component.sns.SnsModifyProfileImageItem
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.viewmodel.sns.SnsProfileModifyViewModel

@Composable
fun SnsProfileModifyScreen(
    viewModel: SnsProfileModifyViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val profileName by viewModel.profileName.collectAsState()
    val name by viewModel.name.collectAsState()
    val introduce by viewModel.introduce.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is BaseViewModel.UiEvent.Navigate -> {
                    navController.navigate(event.target) {
                        if (event.clearBackStack) popUpTo(navController.graph.id) { inclusive = true }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
                is BaseViewModel.UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is BaseViewModel.UiEvent.PopBackStack -> {
                    repeat(event.count) {
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "프로필 편집",
                    style = TextStyle(
                        fontFamily = MainFont,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(vertical = 11.dp, horizontal = 14.dp)
                        .align(Alignment.Center)
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            SnsModifyProfileImageItem(
                imageUrl = "",
                imageChangeClick = { }
            )

            Spacer(modifier = Modifier.height(20.dp))

            EditableTextField(
                title = "프로필 이름",
                value = profileName,
                onValueChange = { viewModel.updateProfileName(it) },
                placeholder = "입력해주세요",
                enabled = true,
                onClick = { }
            )

            Spacer(modifier = Modifier.height(30.dp))

            EditableTextField(
                title = "이름",
                value = name,
                onValueChange = { viewModel.updateName(it) },
                placeholder = "입력해주세요",
                enabled = true,
                onClick = { }
            )

            Spacer(modifier = Modifier.height(30.dp))

            EditableTextField(
                title = "소개",
                value = introduce,
                onValueChange = { viewModel.updateIntroduce(it) },
                placeholder = "나에 대한 설명글을 입력하세요",
                enabled = true,
                onClick = { }
            )
        }
    }
}
