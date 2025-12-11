package com.tlog.ui.component.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import com.tlog.domain.model.share.RecommendTravels
import com.tlog.ui.style.BodyTitle

@Composable
fun RecommendTravelsSection(
    recommendTravels: List<RecommendTravels>,
    onTravelClick: (String) -> Unit
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
                items = recommendTravels,
                key = { travel -> travel.title }
            ) { item ->
                RecommendTravelCard(item, onTravelClick)
            }
        }
    }
}
