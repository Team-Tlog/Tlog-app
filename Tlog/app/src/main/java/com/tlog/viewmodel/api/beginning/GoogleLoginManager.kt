package com.tlog.viewmodel.api.beginning

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.tlog.viewmodel.beginning.login.LoginViewModel

class GoogleLoginManager(
    private val activity: ComponentActivity,
    private val viewModel: LoginViewModel,
    webClientId: String
) {
    private lateinit var launcher: ActivityResultLauncher<Intent>

    init {
        GoogleLoginHelper.init(activity, webClientId)
        registerLauncher()
    }

    private fun registerLauncher() {
        launcher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleLoginHelper.getLoginAccountFromIntent(result.data!!)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken
                if (idToken != null) {
                    Log.d("GoogleLogin", "구글 로그인 성공 - token: $idToken")
                    viewModel.loginToServer("GOOGLE", idToken)
                }
            } catch (e: Exception) {
                Log.e("GoogleLogin", "구글 로그인 실패: ${e.message}")
            }
        }
    }

    fun startLogin() {
        val intent = GoogleLoginHelper.getLoginIntent()
        Log.d("GoogleLogin", "로그인 intent 실행됨")
        launcher.launch(intent)
    }
}
