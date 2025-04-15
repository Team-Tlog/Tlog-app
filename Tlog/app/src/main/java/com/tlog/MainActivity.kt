package com.tlog

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.common.api.ApiException
import com.tlog.data.local.UserPreferences
import com.tlog.ui.navigation.NavHost
import com.tlog.ui.screen.beginning.LoginScreen
import com.tlog.ui.screen.share.CartScreen
import com.tlog.viewmodel.api.beginning.GoogleLoginHelper
import com.tlog.viewmodel.api.beginning.GoogleLoginManager
import com.tlog.viewmodel.api.beginning.LoginViewModel
import com.tlog.viewmodel.api.beginning.LoginViewModelFactory
import com.tlog.viewmodel.api.share.CartViewModel
import com.tlog.viewmodel.api.share.CartViewModelFactory
import com.tlog.viewmodel.share.MainViewModel
import java.security.MessageDigest

class MainActivity : ComponentActivity() {
    // DataStore 선언 (확장 함수로)
    private val ComponentActivity.dataStore by preferencesDataStore(name = "user_prefs")
    private lateinit var googleLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 구글 로그인 초기화
        GoogleLoginHelper.init(this, "838300320724-o0p8ngc8995evp1ph0b9gf4u1dao6hgl.apps.googleusercontent.com")

        // ViewModel
        val loginViewModel = ViewModelProvider(this, LoginViewModelFactory(dataStore))[LoginViewModel::class.java]

        // GoogleLoginManager 이용해서 런처 등록
        val googleLoginManager = GoogleLoginManager(this, loginViewModel, "838300320724-o0p8ngc8995evp1ph0b9gf4u1dao6hgl.apps.googleusercontent.com")
        googleLauncher = googleLoginManager.registerLauncher()

        setContent {
            LoginScreen(
                activity = this,
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

