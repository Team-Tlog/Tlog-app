package com.tlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tlog.data.local.UserPreferences
import com.tlog.ui.navigation.NavHost

import com.tlog.viewmodel.share.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = viewModel()

            val navController = rememberNavController()

            var userId by remember { mutableStateOf<String?>(null) }

            LaunchedEffect(Unit) {
                userId = UserPreferences.getUserId(this@MainActivity) ?: "94e94a78-170a-11f0-b854-02520f3d109f"
            }

            if (userId != null) {
                NavHost(
                    navController = navController,
                    userId = userId!!
                )
            }
        }
    }
}
