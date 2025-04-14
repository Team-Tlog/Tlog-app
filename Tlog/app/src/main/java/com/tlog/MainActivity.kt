package com.tlog

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tlog.data.local.UserPreferences
import com.tlog.ui.navigation.NavHost
import com.tlog.ui.screen.beginning.LoginScreen
import com.tlog.ui.screen.share.CartScreen
import com.tlog.viewmodel.api.beginning.LoginViewModel
import com.tlog.viewmodel.api.beginning.LoginViewModelFactory
import com.tlog.viewmodel.api.share.CartViewModel
import com.tlog.viewmodel.api.share.CartViewModelFactory
import com.tlog.viewmodel.share.MainViewModel
import java.security.MessageDigest

// DataStore 선언 (확장 함수로)
private val ComponentActivity.dataStore by preferencesDataStore(name = "user_prefs")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ✅ 키 해시 뽑는 코드 추가
        getKeyHash()

        setContent {
            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(dataStore)
            )
            LoginScreen(viewModel = loginViewModel)

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

    // ✅ 키 해시 추출 함수
    private fun getKeyHash() {
        try {
            val packageInfo = packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNING_CERTIFICATES
            )
            val signatures = packageInfo.signingInfo?.apkContentsSigners
            if (signatures != null) {
                for (signature in signatures) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    val keyHash = Base64.encodeToString(md.digest(), Base64.NO_WRAP)
                    Log.d("KeyHash", "keyHash: $keyHash")
                }
            }
        } catch (e: Exception) {
            Log.e("KeyHash", "키 해시 추출 실패: ${e.message}", e)
        }
    }
}
