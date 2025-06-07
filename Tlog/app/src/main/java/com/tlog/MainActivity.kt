package com.tlog

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.common.KakaoSdk
import com.tlog.data.local.UserPreferences
import com.tlog.ui.navigation.NavHost
import com.tlog.viewmodel.beginning.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("MainActivity onCreate", BuildConfig.KAKAO_NATIVE_APP_KEY)
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        setContent {
            val navController = rememberNavController()

            val (isLoading, setIsLoading) = remember { mutableStateOf(true) }
            var userId by remember { mutableStateOf<String?>(null) }
            var accessToken by remember { mutableStateOf<String?>(null) }
            var refreshToken by remember { mutableStateOf<String?>(null) }
            var snsId by remember { mutableStateOf<String?>(null) }

            val loginViewModel: LoginViewModel by viewModels()

            LaunchedEffect(Unit) {
                userId = userPreferences.getUserId() ?: ""
                accessToken = userPreferences.getAccessToken()
                refreshToken = userPreferences.getRefreshToken()
                snsId = userPreferences.getSnsId()
                setIsLoading(false)
            }

            // google login
            val context = LocalContext.current
            val googleSignInClient = remember {
                GoogleSignIn.getClient(
                    context,
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
                        .requestEmail()
                        .build()
                )
            }
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
            ) { result ->
                loginViewModel.googleLogin(result.data, navController)
            }
            // google end




            if (isLoading) {
                // 추후 로딩 스크린 (시작 스크린)
            } else {
                val startScreen =
                    if (!userId.isNullOrBlank() && !accessToken.isNullOrBlank() && !refreshToken.isNullOrBlank()) {
                        "main"
                    } else {
                        "login"
                    }
                NavHost(
                    navController = navController,
                    startScreen = startScreen,
                    loginViewModel = loginViewModel,
                    launcher = launcher,
                    snsId = snsId,
                    googleSignInClient = googleSignInClient
                )
            }
        }
    }
}
