package com.tlog.ui.component.share

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont

@Composable
fun TwoMainButtons(
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
    ) {
        // Left Button
        Button(
            onClick = onLeftClick,
            modifier = Modifier
                .width(151.dp)
                .height(55.dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8C8C8C),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "장바구니에 넣기",
                fontSize = 15.sp,
                fontFamily = MainFont,
                fontWeight = FontWeight.ExtraBold
            )
        }

        // Right Button
        Button(
            onClick = onRightClick,
            modifier = Modifier
                .width(151.dp)
                .height(55.dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainColor,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "코스 짜기",
                fontSize = 15.sp,
                fontFamily = MainFont,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
