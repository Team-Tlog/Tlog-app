package com.tlog.ui.component.team

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tlog.R
import com.tlog.ui.style.Body2Regular
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont

@Composable
fun TeamDialog(
    teamCode: String,
    onDismiss: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .width(305.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White.copy(alpha = 0.9f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 30.dp, end = 24.dp, bottom = 41.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "팀원 초대",
                    style = TextStyle(
                        color = MainColor,
                        fontFamily = MainFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                )

                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "팀 코드",
                            style = TextStyle(
                                color = Color.Black,
                                fontFamily = MainFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                        )

                        Text(
                            text = teamCode,
                            style = Body2Regular
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_copy),
                        contentDescription = "팀 코드 복사하기",
                        modifier = Modifier.clickable {
                            clipboardManager.setText(AnnotatedString(teamCode))
                            Toast.makeText(context, "팀 코드가 복사되었습니다.", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }

        }
    }
}