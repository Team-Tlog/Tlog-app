package com.tlog.ui.component.travel

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.data.model.travel.TravelDestinationResponse
import com.tlog.ui.component.share.HashTagsGroup
import com.tlog.ui.theme.MainFont
import coil.compose.AsyncImage

@Composable
fun DestinationCard(
    destination: TravelDestinationResponse,
    isFavorite: Boolean,
    onFavoriteToggle: (String) -> Unit,
    onClick: (TravelDestinationResponse) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(188.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick(destination) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = destination.imageUrl,
                contentDescription = destination.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
                    .padding(start = 31.dp, top = 31.dp, end = 31.dp, bottom = 23.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = destination.name,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontFamily = MainFont,
                            fontWeight = FontWeight.Bold
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_map_pin),
                                contentDescription = null,
                                tint = Color.White,
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = destination.city,
                                color = Color.White,
                                fontFamily = MainFont,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        }
                    }

                    if (isFavorite) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filled_heart),
                            contentDescription = "스크랩됨",
                            modifier = Modifier
                                .size(31.dp)
                                .clickable {
                                    onFavoriteToggle(destination.id)
                                },
                            tint = Color.Red
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_heart),
                            contentDescription = "스크랩 아이콘",
                            modifier = Modifier
                                .size(31.dp)
                                .clickable {
                                    onFavoriteToggle(destination.id)
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(69.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HashTagsGroup(hashTags = destination.tagCountList.map { it.tag })

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
                            text = "${destination.averageRating}(${destination.reviewCount})",
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
