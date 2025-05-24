package com.tlog.ui.screen.share

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tlog.R
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.theme.MainFont


@Composable
fun MainScreen(
    //navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .padding(horizontal = 14.dp)
            ) {

                // 상단바
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.google_login_icon),
                        contentDescription = "Logo",
                        modifier = Modifier.size(38.dp),
                        tint = Color.Unspecified
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "검색",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Black
                    )

                    Icon(
                        painter = painterResource(R.drawable.ic_notification),
                        contentDescription = "알림",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Black
                    )
                }
            }

















        }
    }
}