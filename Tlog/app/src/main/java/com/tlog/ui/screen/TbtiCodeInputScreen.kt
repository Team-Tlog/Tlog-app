package com.tlog.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.component.MainButton
import com.tlog.ui.component.TbtiCodeInputField
import com.tlog.ui.theme.FontBlue
import com.tlog.ui.theme.MainFont


@Preview
@Composable
fun TbtiCodeInputScreen() {
    val focusManager = LocalFocusManager.current
    val codeError = remember { mutableStateOf(false) }
    val isCodeValid = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val topPadding = maxHeight * 0.2f
            val midPadding = maxHeight * 0.06f
            val buttonTopPadding = maxHeight * 0.04f
            val reTestTopPadding = maxHeight * 0.03f
            val sidePadding = maxWidth * 0.07f
            val textSpace = maxWidth * 0.03f


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = topPadding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "TBTI 코드 입력",
                    fontSize = 30.sp,
                    fontFamily = MainFont,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(midPadding))

                LaunchedEffect(textList.map { it.value.text }) {
                    val code = textList.joinToString("") { it.value.text }
                    codeError.value = code.length == 8 && codeError.value // 코드 1자리를 지우면 (수정하려는 움직임이 보이면) false가 되도록
                    isCodeValid.value = code.length == 8 && isCodeValid.value // 코드가 7자리 되면 false가 되도록
                }

                TbtiCodeInputField(
                    textList = textList,
                    requesterList = requesterList,
                    onComplete = { code ->
                        if (isValidTbtiCode(code)) { // 코드 검증
                            Log.d("TbtiCode", "success" + code)
                            focusManager.clearFocus()
                            isCodeValid.value = true // 버튼 활성화를 위해 코드 검증 변수 true
                        } else {
                            Log.d("TbtiCode", "fail" + code)
                            codeError.value = true // 코드 에러 메시지를 띄우기 위해 코드 에러 변수 true
                        }
                    }
                )
                Spacer(modifier = Modifier.height(midPadding))

                if (codeError.value) {
                    LaunchedEffect(key1 = codeError.value) {}
                    Text(
                        text = "올바른 코드를 입력해주세요.",
                        fontFamily = MainFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.Red
                    )
                }

                Spacer(modifier = Modifier.height(buttonTopPadding))

                MainButton(
                    text = "결과 보기",
                    enabled = isCodeValid.value,
                    onClick = {
                        Log.d("resultButton", "my click!!")
                    },
                    modifier = Modifier
                        .padding(horizontal = sidePadding)
                )

                Spacer(modifier = Modifier.height(reTestTopPadding))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(textSpace),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "결과를 잊어버렸어요!",
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "테스트 다시하기",
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.Underline,
                        color = FontBlue,
                        modifier = Modifier
                            .clickable { Log.d("reTest", "my click!!") }
                    )
                }
            }
        }
    }
}

val textList = listOf(
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    )
)

val requesterList = listOf(
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
    FocusRequester()
)

fun isValidTbtiCode(code: String): Boolean { // 우리 TBTI 코드 유형이 맞는지 체크 (2자리씩 끊어서 01~99 사이인지 체크)
    if (code.length != 8 || !code.all { it.isDigit() }) return false

    val split = code.chunked(2) // 2글자씩 나눠서 검증

    return split.all {
        val num = it.toIntOrNull() ?: return@all false
        num in 1..99
    }
}