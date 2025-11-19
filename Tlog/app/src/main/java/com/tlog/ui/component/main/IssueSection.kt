package com.tlog.ui.component.main

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.data.model.share.LocalGuide
import com.tlog.ui.component.travel.BlueHashTagGroup
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.theme.MainFont
import kotlin.collections.forEach
import com.tlog.ui.theme.DefaultImage


@Composable
fun IssueSection(
    localGuides: List<LocalGuide>,
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "ISSUE",
            style = BodyTitle,
            modifier = Modifier
                .padding(start = 24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        localGuides.forEach { issue ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 20.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, issue.infoUrl.toUri())
                        context.startActivity(intent)
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(158.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        AsyncImage(
                            model = issue.imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            error = painterResource(DefaultImage),
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = issue.title,
                        style = TextStyle(
                            fontFamily = MainFont,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = issue.description,
                        style = TextStyle(
                            fontFamily = MainFont,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Light
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    BlueHashTagGroup(issue.property)
                }
            }
        }
    }
}
