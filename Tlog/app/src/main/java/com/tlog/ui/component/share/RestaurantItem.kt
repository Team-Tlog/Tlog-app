package com.tlog.ui.component.share

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.tlog.data.dto.response.restaurant.RestaurantDto
import com.tlog.domain.model.share.Restaurant
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.theme.DefaultImage
import com.tlog.ui.theme.MainFont


@Composable
fun RestaurantItem(
    restaurant: Restaurant,
    context: Context
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, restaurant.infoUrl.toUri())
                context.startActivity(intent)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = restaurant.imageUrl,
            contentDescription = "${restaurant.title} 사진",
            contentScale = ContentScale.Crop,
            error = painterResource(id = DefaultImage),
            modifier = Modifier
                .size(99.dp)
                .clip(RoundedCornerShape(15.dp))
        )

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = restaurant.title,
                style = Body1Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = restaurant.address,
                fontFamily = MainFont,
                fontWeight = FontWeight.Light,
                fontSize = 10.sp,
                modifier = Modifier
                    .height(30.dp)
            )
        }
    }

}