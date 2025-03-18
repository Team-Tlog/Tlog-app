package com.tlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tlog.ui.screen.LoginScreen
import com.tlog.ui.screen.TbtiIntroScreen
import com.tlog.ui.screen.TbtiTestScreen
import com.tlog.ui.theme.TlogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // LoginScreen()
            //TbtiIntroScreen()
            TbtiTestScreen(1, 10)
        }
    }
}
