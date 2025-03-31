package com.tlog.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tlog.ui.theme.MainColor


@Composable
fun MainRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color = MainColor
) {
    Box(
        modifier = Modifier
            //.padding(start = 24.dp)
            .size(24.dp)
            .shadow(3.dp, shape = RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {


        if (selected) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(color = selectedColor, shape = CircleShape)
            )
        }
    }
}