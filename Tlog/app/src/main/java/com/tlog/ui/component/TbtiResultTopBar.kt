package com.tlog.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tlog.R

@Composable
fun TbtiResultTopBar(
    onDownloadClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_download),
            contentDescription = "저장",
            modifier = Modifier
                .size(42.dp)
                .padding(end = 12.dp)
                .clickable(onClick = onDownloadClick)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_share),
            contentDescription = "공유",
            modifier = Modifier
                .size(42.dp)
                .clickable(onClick = onShareClick)
        )
    }
}
