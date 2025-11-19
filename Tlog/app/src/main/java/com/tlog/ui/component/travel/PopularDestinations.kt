package com.tlog.ui.component.travel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.tlog.data.api.PopularDestination
import com.tlog.ui.theme.DefaultImage
import com.tlog.ui.theme.MainFont

@Composable
fun PopularDestinations(
    destinations: List<PopularDestination>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "현재 인기 여행지",
            style = TextStyle(
                fontFamily = MainFont,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp,
                lineHeight = 20.sp
            )
        )
        Spacer(modifier = Modifier.height(14.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = destinations,
                key = { destination -> destination.destinationId }
            ) { destination ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(end = 14.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        if (destination.imageUrl.isNotEmpty()) {
                            AsyncImage(
                                model = destination.imageUrl,
                                contentDescription = destination.region,
                                error = painterResource(id = DefaultImage),
                                modifier = Modifier
                                    .size(96.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(Color.Gray)
                                    .align(Alignment.CenterVertically),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(id = DefaultImage),
                                contentDescription = destination.region,
                                modifier = Modifier
                                    .size(96.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(Color.Gray)
                                    .align(Alignment.CenterVertically),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(modifier = Modifier.align(Alignment.Start)) {
                        Text(
                            text = destination.region,
                            style = TextStyle(
                                fontFamily = MainFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp
                            )
                        )
                    }
                }
            }
        }
    }
}