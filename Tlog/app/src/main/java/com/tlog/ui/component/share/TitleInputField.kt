package com.tlog.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont


@Composable
fun TitleInputField (
    modifier: Modifier = Modifier,
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    singleLine: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        style = BodyTitle,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(25.dp))


    BottomLineInputField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholderText,
        singleLine = singleLine,
    )
}