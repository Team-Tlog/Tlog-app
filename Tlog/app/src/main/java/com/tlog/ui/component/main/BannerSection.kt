package com.tlog.ui.component.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.data.model.share.Banner
import com.tlog.ui.component.share.HashTagsGroup
import com.tlog.ui.theme.DefaultImage


@Composable
fun BannerSection(
    bannerList: List<Banner>,
    onBannerClick: (String, String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            items = bannerList,
            key = { banner -> banner.id }
        ) { banner ->
            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .height(188.dp)
                    .clickable {
                        onBannerClick(banner.title, banner.id)
                    }
            ) {
                AsyncImage(
                    model = banner.imageUrl,
                    contentDescription = "배너",
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = DefaultImage),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp))
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 26.dp, bottom = 20.dp, start = 29.dp)
                ) {
                    Text(
                        text = banner.title,
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(66.dp))

                    HashTagsGroup(banner.hashtags)
                }
            }
        }
    }
}