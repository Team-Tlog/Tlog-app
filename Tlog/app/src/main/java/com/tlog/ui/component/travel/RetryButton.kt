package com.tlog.ui.component.travel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.theme.MainColor

@Composable
fun RetryButton(
    text: String = "다시 추천받기",
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(160.dp)
            .height(40.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = MainColor
        ),
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(1.dp, MainColor)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(25.5.dp))

            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(10.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = "다시 추천",
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 12.dp)
            )
        }
    }
}
