package com.tlog.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TbtiCodeInputField(
    textList: List<MutableState<TextFieldValue>>,
    requesterList: List<FocusRequester>,
    onComplete: (String) -> Unit
) {
    Surface (
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
               modifier = Modifier
                   .padding(horizontal = 16.dp)
                   .padding(top = 50.dp)
                   .align(Alignment.TopCenter)
            ) {
                for (i in textList.indices) { // 입력 칸 개수만큼 입력 칸 생성
                    InputFeild(
                        value = textList[i].value,
                        onValueChange = { newValue ->
                            val oldText = textList[i].value.text
                            val input = newValue.text.filter { it.isDigit() }

                            if (oldText.isNotEmpty() && input.isEmpty()) {
                                if(i != 0)
                                    requesterList[i - 1].requestFocus()
                                textList[i].value = TextFieldValue("", TextRange(0))
                                return@InputFeild
                            }

                            if (input.isNotEmpty()) {
                                for ((offset, char) in input.withIndex()) {
                                    val targetIndex = i + offset
                                    if (targetIndex < textList.size)
                                        textList[targetIndex].value = TextFieldValue(char.toString(), TextRange(1))
                                }

                                val nextIndex = i + input.length
                                if (nextIndex < requesterList.size)
                                    requesterList[nextIndex].requestFocus()

                                val code = textList.joinToString("") { it.value.text }
                                if (code.length == textList.size)
                                    onComplete(code)
                            }
                        },
                        focusRequester = requesterList[i]
                    )
                }
            }
        }
    }
}

@Composable
fun InputFeild(
    value: TextFieldValue,
    onValueChange: (value: TextFieldValue) -> Unit,
    focusRequester: FocusRequester
) {
    BasicTextField(
        readOnly = false,
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF3F3F3))
            .wrapContentSize()
            .focusRequester(focusRequester),
        maxLines = 1,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .width(35.dp)
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                innerTextField()
            }
        },
        cursorBrush = SolidColor(Color.White),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = null
        )
    )
}