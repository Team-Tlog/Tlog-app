package com.tlog.ui.component.share

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tlog.R


@Composable
fun MainTopBar(
    searchIconClickable: () -> Unit,
    notificationIconClickable: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 14.dp)
    ) {

        // 상단바
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.google_login_icon),
                contentDescription = "Logo",
                modifier = Modifier.size(38.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = {
                searchIconClickable()
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "검색",
                    modifier = Modifier
                        .size(40.dp),
                    tint = Color.Black
                )
            }
            IconButton(onClick = {
                notificationIconClickable()
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_notification),
                    contentDescription = "알림",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Black
                )
            }
        }
    }
}