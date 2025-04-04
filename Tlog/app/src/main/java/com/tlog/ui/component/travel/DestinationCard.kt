package com.tlog.ui.component.travel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.tlog.data.model.TravelDestinationData
import com.tlog.ui.component.share.HashTagsGroup
import com.tlog.ui.theme.MainFont

@Composable
fun DestinationCard(
    destination: TravelDestinationData,
    onFavoriteToggle: (String) -> Unit,
    onClick: (TravelDestinationData) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .width(312.dp)
            .height(188.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick(destination) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
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
                                text = destination.location,
                                color = Color.White,
                                fontFamily = MainFont,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Icon(
                        imageVector = if (destination.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (destination.isFavorite) Color.Red else Color.White,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { onFavoriteToggle(destination.id) }
                    )
                }
                Spacer(modifier = Modifier.height(69.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HashTagsGroup(hashTags = destination.tags)

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
                            text = "${destination.rating}(${destination.reviewCount})",
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