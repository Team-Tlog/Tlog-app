package com.tlog.ui.component.share

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tlog.R
import com.tlog.ui.style.Body1Bold


@Composable
fun NotFound(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = Body1Bold,
    characterSize: Dp = 150.dp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.character_error),
            contentDescription = null,
            modifier = Modifier
                .size(characterSize)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = text,
            style = textStyle
        )
    }
}
