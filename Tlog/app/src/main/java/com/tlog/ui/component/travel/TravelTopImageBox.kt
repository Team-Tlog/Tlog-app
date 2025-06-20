package com.tlog.ui.component.travel

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tlog.R


@Composable
fun TravelTopImageBox(
    imageUrl: String,
    isScrap: Boolean,
    clickScrap: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(319.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "여행지 사진",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
            error = painterResource(id = R.drawable.tmp_jeju)
        )

        TravelInfoTopBar(
            //iconList = listOf(R.drawable.ic_heart),
            topBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
            isScrap = isScrap,
            clickScrap = clickScrap
        )
    }
}