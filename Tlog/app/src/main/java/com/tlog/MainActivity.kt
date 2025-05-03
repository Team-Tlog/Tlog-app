package com.tlog

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.datastore.preferences.preferencesDataStore
import com.tlog.ui.screen.beginning.LoginScreen
import com.tlog.viewmodel.api.beginning.GoogleLoginManager
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tlog.data.local.UserPreferences
import com.tlog.ui.navigation.NavHost
import com.tlog.viewmodel.beginning.login.LoginViewModel
import com.tlog.viewmodel.share.MainViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val loginViewModel = LoginViewModel(
            userPreferences = UserPreferences,
            context = this
        )

        val googleLoginManager = GoogleLoginManager(
            activity = this,
            viewModel = loginViewModel,
            webClientId = BuildConfig.GOOGLE_CLIENT_ID
        )

        setContent {
            val mainViewModel: MainViewModel = viewModel()

            val navController = rememberNavController()

            var userId by remember { mutableStateOf<String?>(null) }
            var accessToken by remember { mutableStateOf<String?>(null) }
            var refreshToken by remember { mutableStateOf<String?>(null) }

            LaunchedEffect(Unit) {
                userId = UserPreferences.getUserId(this@MainActivity) //?: "94e94a78-170a-11f0-b854-02520f3d109f"
                accessToken = UserPreferences.getAccessToken(this@MainActivity)
                refreshToken = UserPreferences.getRefreshToken(this@MainActivity)
            }
            mainViewModel.set(userId, accessToken, refreshToken)



            LoginScreen(
                viewModel = loginViewModel,
                onGoogleLoginClick = {
                    googleLoginManager.startLogin()
                }
            )

            Log.d("MainActivity", "hi "+mainViewModel.userId.value?:"없음")
            Log.d("MainActivity", "hi2 "+mainViewModel.accessToken.value?:"없음")
            Log.d("MainActivity", "hi3 "+mainViewModel.refreshToken.value?:"없음")


            if (userId != null && accessToken != null) {
                NavHost(
                    navController = navController,
                    mainViewModel = mainViewModel
                )
            }
        }


    }
}
