package com.tlog.ui.component.travel

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.component.share.LazyHashTagsGroup
import com.tlog.ui.theme.DefaultImage
import com.tlog.ui.theme.MainFont
import coil.compose.AsyncImage
import com.tlog.domain.model.travel.Travel

@Composable
fun TravelCard(
    travel: Travel,
    isFavorite: () -> Boolean,
    onFavoriteToggle: () -> Unit,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick(travel.travelId) }
            .heightIn(max = 200.dp)
            .background(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
                AsyncImage(
                    model = travel.imageUrl,
                    contentDescription = travel.travelName,
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = DefaultImage),
                    modifier = Modifier
                        .fillMaxWidth()
                )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 31.dp, top = 31.dp, end = 31.dp, bottom = 23.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column (
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = travel.travelName,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontFamily = MainFont,
                            maxLines = 1,
                            overflow = TextOverflow.Clip,
                            fontWeight = FontWeight.Bold,
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_map_pin),
                                contentDescription = null,
                                tint = Color.White,
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = travel.city,
                                color = Color.White,
                                fontFamily = MainFont,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Icon(
                        painter = painterResource(if(isFavorite()) R.drawable.ic_filled_heart else R.drawable.ic_heart),
                        contentDescription = "스크랩" + if (isFavorite()) "됨" else "버튼",
                        modifier = Modifier
                            .size(31.dp)
                            .clickable { onFavoriteToggle() },
                        tint = if (isFavorite()) Color.Red else Color.Unspecified,
                    )
                }

                Spacer(modifier = Modifier.height(69.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LazyHashTagsGroup(hashTags = travel.hashTags)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filled_star),
                            contentDescription = null,
                            tint = Color.Yellow,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            fontFamily = MainFont,
                            text = "${travel.rating}(${travel.reviewCount})",
                            color = Color.White,
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
