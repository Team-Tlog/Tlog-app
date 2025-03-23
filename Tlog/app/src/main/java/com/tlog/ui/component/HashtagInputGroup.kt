package com.tlog.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont

@Composable
fun HashtagInputGroup(
    text: String = "해시태그",
    value: String, // hashTag
    singleLine: Boolean = true,
    placeholderText: String,
    hashTags: List<String>,
    onValueChange: (String) -> Unit,
    onAddHashtag: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier
) {
    val defaultModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp)
        .shadow(3.dp, shape = RoundedCornerShape(24.dp))
        .clip(RoundedCornerShape(24.dp))
        .background(Color.White)

    Text(
        text = text,
        fontFamily = MainFont,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        modifier = Modifier.padding(start = 24.dp)
    )

    Spacer(modifier = Modifier.height(10.dp))

    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(hashTags) { tag ->
            val textWidth = 4 * tag.length + 25 // 박스 크기

            Surface(
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(3.dp, RoundedCornerShape(50))
                    .background(Color.White)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(22.dp)
                        .width(textWidth.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "#$tag",
                        fontSize = 8.sp,
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }


    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(
            text = placeholderText,
            fontSize = 13.sp,
            fontFamily = MainFont,
            fontWeight = FontWeight.Thin
        ) },
        singleLine = singleLine,
        shape = RoundedCornerShape(45),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = MainColor
        ),
        textStyle = TextStyle(
            fontFamily = MainFont,
            fontWeight = FontWeight.Thin
        ),
        modifier = defaultModifier.then(modifier),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )


}