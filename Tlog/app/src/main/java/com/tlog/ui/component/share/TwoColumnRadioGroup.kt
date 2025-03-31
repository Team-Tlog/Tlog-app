package com.tlog.ui.component.share

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainFont

@Composable
fun TwoColumnRadioGroup(
    title: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
) {
    Text(
        text = title,
        fontFamily = MainFont,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        //modifier = Modifier.padding(start = 24.dp)
    )

    Spacer(modifier = Modifier.height(24.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .selectableGroup()
    ) {
        options.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { text ->
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MainRadioButton(
                            selected = (text == selectedOption),
                            onClick = {onOptionSelected(text)}
                        )

                        Text(
                            text = text,
                            fontFamily = MainFont,
                            modifier = Modifier.padding(start = 10.dp)
                        )

                        Spacer(modifier = Modifier.width(30.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}