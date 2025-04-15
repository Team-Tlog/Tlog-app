package com.tlog.viewmodel.api.beginning

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.util.Log
import com.google.android.gms.common.api.ApiException

class GoogleLoginManager(
    private val activity: ComponentActivity,
    private val viewModel: LoginViewModel,
    private val webClientId: String
) {
    lateinit var launcher: ActivityResultLauncher<Intent>

    fun registerLauncher(): ActivityResultLauncher<Intent> {
        GoogleLoginHelper.init(activity, webClientId)

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

        return launcher
    }
}
