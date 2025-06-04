package com.tlog.ui.component.travel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainFont
import com.tlog.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TravelCategoryGrid(
    modifier: Modifier = Modifier
) {
    val categories = listOf("전국", "서울", "대구", "부산")
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        items(categories) { category ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 14.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.tmp_jeju),
                        contentDescription = "임시 이미지",
                        modifier = Modifier
                            .size(96.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.Gray)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier.align(Alignment.Start)) {
                    Text(
                        text = category,
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