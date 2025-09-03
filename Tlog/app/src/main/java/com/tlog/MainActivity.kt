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
import com.kakao.vectormap.KakaoMapSdk
import com.tlog.data.local.UserPreferences
import com.tlog.ui.navigation.NavHost
import com.tlog.viewmodel.beginning.LoginViewModel
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
            val navController = rememberNavController()

            val (isLoading, setIsLoading) = remember { mutableStateOf(true) }
            var userId by remember { mutableStateOf<String?>(null) }
            var accessToken by remember { mutableStateOf<String?>(null) }
            var refreshToken by remember { mutableStateOf<String?>(null) }

            val loginViewModel: LoginViewModel by viewModels()
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
//                if (ContextCompat.checkSelfPermission(
//                        context,
//                        android.Manifest.permission.POST_NOTIFICATIONS
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//                }
//            }
//            }

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

                LaunchedEffect(navController, startScreen) {
                    val type = intent.getStringExtra("type")
                    if (type != null) {

                        kotlinx.coroutines.delay(100)

                        try {
                            when (type) {
                                "1" -> {}
                                "2" -> {
                                    val linkType = intent.getStringExtra("linkType")
                                    Log.d("MainActivity", "linkType : $linkType")
                                    when (linkType) {
                                        "1" -> {} // mainScreen이라 놔둬도 됨
                                        "2" -> navController.navigate("myPage")
                                        else -> {
                                            val linkAddress = intent.getStringExtra("linkAddress")
                                            Log.d("MainActivity", "linkAddress : $linkAddress")

                                            when (linkType) {
                                                "10" -> navController.navigate("travelInfo/$linkAddress")
                                                "11" -> navController.navigate("snsPostDetail/$linkAddress")
                                                "12" -> navController.navigate("snsMyPage/$linkAddress")
                                                "13" -> {} // 채팅방 이동 (채팅방 생기면 ㄱㄱ)
                                                else -> Log.d("MainActivity", "알림 타입 오류")
                                            }
                                        }
                                    }
                                }

                                "10" -> {
                                    val objectId = intent.getStringExtra("objectId")
                                    navController.navigate("snsPostDetail/$objectId")
                                }

                                "11" -> {
                                    val actorId = intent.getStringExtra("actorId")
                                    navController.navigate("snsMyPage/$actorId")
                                }
                            }
                        } catch (e: Exception) {
                            Log.e("MainActivity", "Navigation error: ${e.message}")
                        }
                    }
                }
            }
        }
    }
}
