package com.tlog

import android.content.pm.PackageManager
import android.os.Build
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.common.KakaoSdk
import com.kakao.vectormap.KakaoMapSdk
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

        // kakao map
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        KakaoMapSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        setContent {

            val context = LocalContext.current

            // 알림 권한 요청
//            val notificationPermissionLauncher = rememberLauncherForActivityResult(
//                contract = ActivityResultContracts.RequestPermission()
//            ) { isGranted ->
//                if (isGranted) {
//                    Log.d("Permission", "알림 권한 허용됨")
//                } else {
//                    Log.d("Permission", "알림 권한 거부됨")
//                }
//            }
//
//            LaunchedEffect(Unit) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                    if (ContextCompat.checkSelfPermission(
//                            context,
//                            android.Manifest.permission.POST_NOTIFICATIONS
//                        ) != PackageManager.PERMISSION_GRANTED
//                    ) {
//                        notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//                    }
//                }
//            }

            val navController = rememberNavController()

            val (isLoading, setIsLoading) = remember { mutableStateOf(true) }
            var userId by remember { mutableStateOf<String?>(null) }
            var accessToken by remember { mutableStateOf<String?>(null) }
            var refreshToken by remember { mutableStateOf<String?>(null) }

            val loginViewModel: LoginViewModel by viewModels()

            LaunchedEffect(Unit) {
                userId = userPreferences.getUserId() ?: ""
                accessToken = userPreferences.getAccessToken()
                refreshToken = userPreferences.getRefreshToken()
                setIsLoading(false)
            }

            // google login
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
                    googleSignInClient = googleSignInClient
                )
            }
        }
    }
}
