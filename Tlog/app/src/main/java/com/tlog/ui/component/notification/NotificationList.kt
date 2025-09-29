package com.tlog.ui.component.notification

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.data.model.notification.NotificationItem
import com.tlog.ui.theme.MainFont
import com.tlog.util.toTimeString

@Composable
fun NotificationList(
    notificationList: List<NotificationItem>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        items(
            items = notificationList,
            key = { notification -> "${notification.content}${notification.timestamp}" }
        ) { item ->
            NotificationItem(content = item.content, date = item.timestamp.toTimeString())

            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFF0F0F0),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun NotificationItem(content: String, date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_notification_light),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = content,
                style = TextStyle(
                    fontFamily = MainFont,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = date,
                    style = TextStyle(
                        fontFamily = MainFont,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF727272)
                    ),
                )
            }
        }
    }

}