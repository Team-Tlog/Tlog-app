package com.tlog.ui.component.share

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tlog.ui.style.BodyTitle


@Composable
fun TitleInputField (
    modifier: Modifier = Modifier,
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    singleLine: Boolean = true,
    spacerHeight: Dp = 25.dp,
) {

    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = text,
            style = BodyTitle,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(spacerHeight))


        BottomLineInputField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholderText,
            singleLine = singleLine,
        )
    }
}