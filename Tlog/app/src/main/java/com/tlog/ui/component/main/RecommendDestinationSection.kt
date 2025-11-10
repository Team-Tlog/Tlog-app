package com.tlog.ui.component.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.data.model.share.Destination
import com.tlog.data.model.share.RecommendDestination
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.theme.MainFont


@Composable
fun DestinationItem(
    travel: Destination
) {
    Row(
        modifier = Modifier
            .height(34.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = travel.imageUrl,
            contentDescription = "travel",
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.tmp_jeju),
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(9.dp))

        Text(
            text = travel.name,
            style = TextStyle(
                fontFamily = MainFont,
                fontSize = 10.sp,
                fontWeight = FontWeight.Light,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = travel.name,
            tint = Color.Black,
            modifier = Modifier
                .size(24.dp)
        )
    }
}

@Composable
fun DestinationCard(
    destination: RecommendDestination
) {
    Column(
        modifier = Modifier
            .width(320.dp)
            .padding((0.5).dp)
            .shadow(1.dp, RoundedCornerShape(10.dp))
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.tmp_flower),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(123.dp)
            )

            // 뷸러
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = 95.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.White)
                        )
                    )
            )
        }

        Spacer(modifier = Modifier.height(23.dp))


        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 20.dp)
            ) {
                Column {
                    Text(
                        text = destination.title,
                        style = Body1Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = destination.description,
                        style = TextStyle(
                            fontFamily = MainFont,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .size(height = 31.dp, width = 63.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color(0xFFF0F5FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "더보기",
                            style = TextStyle(
                                fontFamily = MainFont,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF676767)
                            )
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        // 더보기
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_circle),
                            contentDescription = "plus",
                            tint = Color(0xFF676767),
                            modifier = Modifier.size(11.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 23.dp, end = 15.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                destination.destinations.forEach { travel ->
                    DestinationItem(travel)
                }
            }
        }
    }
}

@Composable
fun RecommendDestinationSection(
    recommendDestinations: List<RecommendDestination>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .background(Color.White)
    ) {
        Text(
            text = "추천 여행지",
            style = BodyTitle,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(15.dp))


        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        ) {
            items(
                items = recommendDestinations,
                key = { travel -> travel.title }
            ) { item ->
                DestinationCard(item)
            }
        }
    }
}