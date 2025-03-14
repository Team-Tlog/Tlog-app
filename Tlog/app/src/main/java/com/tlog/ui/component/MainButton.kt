package com.tlog.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainColor

@Composable
fun MainButton(
    text : String,
    onClick : () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(50), // 좌우를 둥글게
        colors = ButtonDefaults.buttonColors(
            containerColor = MainColor,
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}