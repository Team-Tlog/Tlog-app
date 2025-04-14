package com.tlog.ui.component.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tlog.ui.screen.share.NotificationTabType
import com.tlog.ui.style.Body1Regular
import com.tlog.ui.theme.MainColor

@Composable
fun NotificationTab(
    selectedTab: NotificationTabType,
    onTabSelected: (NotificationTabType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        NotificationTabItem(tab = NotificationTabType.News, selectedTab = selectedTab, onTabSelected, Modifier.weight(1f))
        NotificationTabItem(tab = NotificationTabType.TSNS, selectedTab = selectedTab, onTabSelected, Modifier.weight(1f))
    }
}

@Composable
fun NotificationTabItem(
    tab: NotificationTabType,
    selectedTab: NotificationTabType,
    onTabSelected: (NotificationTabType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable { onTabSelected(tab) }
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = tab.title, style = Body1Regular, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        if (tab == selectedTab) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(MainColor)
            )
        } else {
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(3.dp))
        }
    }
}
