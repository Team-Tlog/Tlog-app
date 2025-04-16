package com.tlog

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.tlog.ui.screen.beginning.LoginScreen
import com.tlog.viewmodel.api.beginning.GoogleLoginManager
import com.tlog.viewmodel.api.beginning.LoginViewModel
import com.tlog.viewmodel.api.beginning.LoginViewModelFactory

class MainActivity : ComponentActivity() {
    private val ComponentActivity.dataStore by preferencesDataStore(name = "user_prefs")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val loginViewModel = ViewModelProvider(this, LoginViewModelFactory(dataStore))[LoginViewModel::class.java]

        val googleLoginManager = GoogleLoginManager(
            activity = this,
            viewModel = loginViewModel,
            webClientId = BuildConfig.GOOGLE_WEB_CLIENT_ID
        )

        setContent {
            LoginScreen(
                viewModel = loginViewModel,
                onGoogleLoginClick = {
                    googleLoginManager.startLogin()
                }
            )
        }


            /*
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
            */
        }

}