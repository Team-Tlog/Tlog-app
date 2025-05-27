package com.tlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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

            val loginViewModel = LoginViewModel(
                userPreferences = UserPreferences,
                context = this
            )

            LaunchedEffect(Unit) {
                userId =
                    UserPreferences.getUserId(this@MainActivity) ?: "9888b62b-170a-11f0-b854-02520f3d109f"
                accessToken = UserPreferences.getAccessToken(this@MainActivity)
                refreshToken = UserPreferences.getRefreshToken(this@MainActivity)
            }
            mainViewModel.set(userId, accessToken, refreshToken)

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


            val startScreen = if (userId != null && accessToken != null) "main" else "login"

            NavHost(
                navController = navController,
                startScreen = startScreen,
                loginViewModel = loginViewModel,
                mainViewModel = mainViewModel,
                launcher = launcher,
                googleSignInClient = googleSignInClient
            )




        }
    }
}