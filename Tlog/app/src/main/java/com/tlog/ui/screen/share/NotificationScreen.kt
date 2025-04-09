package com.tlog.ui.screen.share

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tlog.ui.component.share.BottomBar
import com.tlog.ui.component.share.TopBar
import com.tlog.ui.component.notification.NotificationTab
import com.tlog.ui.component.notification.AppNotificationList
import com.tlog.ui.component.notification.TsnsNotificationList
import com.tlog.viewmodel.share.NotificationViewModel

@Composable
fun NotificationScreen(
    navController: NavController,
    previousSelectedIndex: Int
) {
    var selectedTab by remember { mutableStateOf(NotificationTabType.News) }
    val viewModel: NotificationViewModel = viewModel()

    Column(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        TopBar(text = "알림")
        NotificationTab(selectedTab = selectedTab, onTabSelected = { selectedTab = it })

        when (selectedTab) {
            NotificationTabType.News -> AppNotificationList(viewModel = viewModel)
            NotificationTabType.TSNS -> TsnsNotificationList()
        }

        Spacer(modifier = Modifier.weight(1f))
        BottomBar(navController = navController, selectedIndex = previousSelectedIndex, )
    }
}

enum class NotificationTabType(val title: String) {
    News("새 소식"),
    TSNS("T-SNS")
}
