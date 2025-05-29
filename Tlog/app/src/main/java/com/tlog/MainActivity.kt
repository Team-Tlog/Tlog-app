package com.tlog

import android.os.Bundle
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.tlog.data.local.UserPreferences
import com.tlog.ui.navigation.NavHost
import com.tlog.viewmodel.beginning.login.LoginViewModel
import com.tlog.viewmodel.share.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            val mainViewModel: MainViewModel = viewModel()
            val navController = rememberNavController()
            var userId by remember { mutableStateOf<String?>(null) }
            var accessToken by remember { mutableStateOf<String?>(null) }
            var refreshToken by remember { mutableStateOf<String?>(null) }

            val loginViewModel: LoginViewModel by viewModels()

            LaunchedEffect(Unit) {
                userId =
                    UserPreferences.getUserId(this@MainActivity) ?: "9888b62b-170a-11f0-b854-02520f3d109f"
                accessToken = UserPreferences.getAccessToken(this@MainActivity)
                refreshToken = UserPreferences.getRefreshToken(this@MainActivity)
            }
            mainViewModel.set(userId, accessToken, refreshToken) // 추후 뷰모델에서 알아서 실행되는걸로 바꾸고 hilt 갑시닷 혹은 이 위에 ㄱㄱ

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


            val isReady = userId != null && accessToken != null && refreshToken != null

            if (isReady) {
                NavHost(
                    navController = navController,
                    startScreen = "main",
                    loginViewModel = loginViewModel,
                    mainViewModel = mainViewModel,
                    launcher = launcher,
                    googleSignInClient = googleSignInClient
                )
            } else {
                // 로딩 화면 보여주기 (또는 아무것도 안 보여주기) 시작화면 만들어지면 보여주면 될 듯!

            }




        }
    }
}