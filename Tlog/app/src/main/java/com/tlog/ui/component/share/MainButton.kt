package com.tlog.ui.component.share

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
import com.tlog.ui.theme.MainFont

@Composable
fun MainButton(
    text : String,
    enabled: Boolean = true,
    buttonColor: Color = MainColor,
    onClick : () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp),
        enabled = enabled,
        shape = RoundedCornerShape(20), // 좌우를 둥글게
        colors = ButtonDefaults.buttonColors(
            // 활성화 시 컬러
            containerColor = MainColor,
            contentColor = Color.White,

            // 비활성화 시 컬러
            disabledContainerColor = Color(0xFFCECECE),
            disabledContentColor = Color.White
        )
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontFamily = MainFont,
            fontWeight = FontWeight.Medium
        )
    }
}