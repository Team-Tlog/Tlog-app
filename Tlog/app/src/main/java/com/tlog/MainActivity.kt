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
import com.tlog.viewmodel.api.beginning.GoogleLoginHelper
import com.tlog.viewmodel.api.beginning.GoogleLoginManager
import com.tlog.viewmodel.api.beginning.LoginViewModel
import com.tlog.viewmodel.api.beginning.LoginViewModelFactory

class MainActivity : ComponentActivity() {
    // DataStore 선언 (확장 함수로)
    private val ComponentActivity.dataStore by preferencesDataStore(name = "user_prefs")
    private lateinit var googleLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 구글 로그인 초기화
        GoogleLoginHelper.init(this, BuildConfig.GOOGLE_WEB_CLIENT_ID)

        // ViewModel
        val loginViewModel = ViewModelProvider(this, LoginViewModelFactory(dataStore))[LoginViewModel::class.java]

        // GoogleLoginManager 이용해서 런처 등록
        val googleLoginManager = GoogleLoginManager(this, loginViewModel, BuildConfig.GOOGLE_WEB_CLIENT_ID)
        googleLauncher = googleLoginManager.registerLauncher()

        setContent {
            LoginScreen(
                viewModel = loginViewModel,
                googleLauncher = googleLauncher
            )
        }
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

