package com.tlog

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.tlog.ui.screen.beginning.LoginScreen
import com.tlog.viewmodel.api.beginning.GoogleLoginManager
import com.tlog.viewmodel.api.beginning.LoginViewModel
import com.tlog.viewmodel.api.beginning.LoginViewModelFactory
import java.security.MessageDigest
import android.content.pm.PackageManager
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tlog.data.local.UserPreferences
import com.tlog.ui.navigation.NavHost
import com.tlog.ui.screen.sns.SnsPostWriteDetailScreen
import com.tlog.viewmodel.share.MainViewModel


class MainActivity : ComponentActivity() {
    private val ComponentActivity.dataStore by preferencesDataStore(name = "user_prefs")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //SHA1 키 로그 출력
        printSha1Key()

        val loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory(dataStore))[LoginViewModel::class.java]

        val googleLoginManager = GoogleLoginManager(
            activity = this,
            viewModel = loginViewModel,
            webClientId = BuildConfig.GOOGLE_CLIENT_ID
        )

        setContent {
//            LoginScreen(
//                viewModel = loginViewModel,
//                onGoogleLoginClick = {
//                    googleLoginManager.startLogin()
//                }
//            )
            SnsPostWriteDetailScreen()
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
        
        
        
        
         /* DI
            LaunchedEffect(Unit) {
                userId = UserPreferences.getUserId(this@MainActivity) ?: "94e94a78-170a-11f0-b854-02520f3d109f"
            }
            mainViewModel.set(userId)

            if (userId != null) {
                NavHost(
                    navController = navController,
                    mainViewModel = mainViewModel
                )
                */
                
    }

    //sha1키 출력하는 함수
    private fun printSha1Key() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            val signatures = info.signingInfo?.apkContentsSigners
            if (signatures != null) {
                for (signature in signatures) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    val sha1Bytes = md.digest()
                    val sha1Hex = sha1Bytes.joinToString(":") { "%02X".format(it) }
                    Log.d("SHA1_KEY", "현재 SHA1 키: $sha1Hex")
                }
            } else {
                Log.e("SHA1_KEY", "서명 정보가 없습니다.")
            }
        } catch (e: Exception) {
            Log.e("SHA1_KEY", "SHA1 키 추출 실패: ${e.message}")
        }
    }

}
