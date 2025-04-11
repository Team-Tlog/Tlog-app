package com.tlog.ui.component.share

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.style.Body1Regular
import com.tlog.ui.theme.MainFont

@Composable
fun TextButtonTopBar(
    title: String,
    btnText: String,
    btnClickable: () -> Unit,
    modifier: Modifier = Modifier
) {
    val defaultModifier = Modifier.height(42.dp)
    Box(
        modifier = defaultModifier.then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = title,
            fontSize = 20.sp,
            fontFamily = MainFont,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = (21.5).dp)
                .clickable { btnClickable() }
        ) {
            Text(
                text = btnText,
                style = Body1Regular,
            )
        }
    }
}