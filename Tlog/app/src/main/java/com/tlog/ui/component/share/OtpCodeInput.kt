package com.tlog.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
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
fun OtpCodeInput(
    textList: List<MutableState<TextFieldValue>>,
    requesterList: List<FocusRequester>,
    isNumber: Boolean = true,
    onComplete: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val currentFocusIndex = remember { mutableStateOf(0) } // 포커스 1개로만 관리하기 위해서 만든 변수

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 50.dp)
                    .align(Alignment.TopCenter)
            ) {
                for (i in textList.indices) {
                    val requester = requesterList[i]

                    // 현재 포커스 인덱스에 해당하는 필드에만 포커스 요청하도록
                    LaunchedEffect(currentFocusIndex.value) {
                        if (currentFocusIndex.value == i)
                            requester.requestFocus()
                    }


                    InputFeild(
                        value = textList[i].value,
                        onValueChange = { newValue ->
                            val input = if (isNumber) newValue.text.filter { it.isDigit() } else newValue.text
                            val oldText = textList[i].value.text

                            // 값이 있다가 사라질 경우 (지워질 경우) 포커스 이동
                            if (oldText.isNotEmpty() && input.isEmpty()) {
                                textList[i].value = TextFieldValue("", TextRange(0))
                                if (i > 0)
                                    currentFocusIndex.value = i - 1
                                return@InputFeild
                            }

                            // 붙여넣기 할 경우 갈라서 넣어주고 포커스 옮겨주기
                            if (input.isNotEmpty()) {
                                val maxLength = textList.size - i // 최대 몇 글자까지 입력 가능한지 -> 붙여넣기 시 필드 초과하는 것 방지
                                val subInput = input.take(maxLength) // 남은 길이만큼 자름 -> List 사이즈 = 8 / 1번필드 + "123456789" = "12345678" (9 잘림)

                                for ((offset, char) in subInput.withIndex()) {
                                    val targetIndex = i + offset
                                    if (targetIndex < textList.size) {
                                        textList[targetIndex].value = TextFieldValue(
                                            char.toString(),
                                            TextRange(1)
                                        )
                                    }
                                }

                                val code = textList.joinToString("") { it.value.text }
                                val isComplete = code.length == textList.size

                                if (isComplete) {
                                    focusManager.clearFocus()
                                    onComplete(code)
                                } else {
                                    val nextIndex = i + subInput.length
                                    if (nextIndex < requesterList.size)
                                        currentFocusIndex.value = nextIndex
                                    else
                                        focusManager.clearFocus()
                                }
                            }
                        },
                        isNumber = isNumber,
                        focusRequester = requester
                    )
                }
            }
        }
    }
}

@Composable
fun InputFeild(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    isNumber: Boolean = true,
    focusRequester: FocusRequester
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF3F3F3))
            .wrapContentSize()
            .focusRequester(focusRequester),
        maxLines = 1,
        cursorBrush = SolidColor(Color.White),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = if(isNumber) KeyboardType.Number else KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = null
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .width(35.dp)
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                innerTextField()
            }
        }
    )
}