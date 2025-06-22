package com.tlog.ui.component.share

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainFont

@Composable
fun HashtagInputGroup(
    modifier: Modifier = Modifier,
    text: String = "해시태그",
    value: String, // hashTag
    singleLine: Boolean = true,
    placeholderText: String,
    hashTags: List<String>,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    Text(
        text = text,
        fontFamily = MainFont,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
    )

    Spacer(modifier = Modifier.height(10.dp))

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(hashTags) { tag ->
            BlueHashTag(tag = tag)
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    BottomLineInputField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholderText,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier
    )


}