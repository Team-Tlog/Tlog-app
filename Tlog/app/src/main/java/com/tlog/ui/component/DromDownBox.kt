package com.tlog.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tlog.R
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownBox(
    expanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit,
    options: List<String>,
    value: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier
) {
    ExposedDropdownMenuBox (
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bottom_arrow), // 드롭다운 버튼 설정
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White, // 드롭다운 전 색깔
                focusedIndicatorColor = MainColor,
                unfocusedLabelColor = Color(0xFFA9A9A9),
                focusedTextColor = Color.Black,
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()

        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            options.forEach { selectOption ->
                DropdownMenuItem(
                    text = { Text(
                        text = selectOption,
                        fontFamily = MainFont,
                    ) },
                    onClick = {
                        onOptionSelected(selectOption)
                        onExpandedChange(false)
                    }
                )

            }
        }
    }

}