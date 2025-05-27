package com.tlog.viewmodel.api.beginning

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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
            val task = GoogleLoginHelper.getLoginAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d("GoogleLogin", "Google 로그인 성공 - ID Token: ${account.idToken}")

                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener(activity) { authResult ->
                        if (authResult.isSuccessful) {
                            val firebaseUser = FirebaseAuth.getInstance().currentUser
                            Log.d("GoogleLogin", "Firebase 로그인 성공: ${firebaseUser?.email}")

                            // 서버 전송용 ID 토큰 획득
                            firebaseUser?.getIdToken(false)?.addOnSuccessListener { result ->
                                val token = result.token
                                //viewModel.loginToServer("GOOGLE", token, navController)
                            }
                        } else {
                            Log.w("GoogleLogin", "Firebase 로그인 실패", authResult.exception)
                        }
                    }

            } catch (e: ApiException) {
                Log.w("GoogleLogin", "Google 로그인 실패", e)
            }
        }
    }

    fun startLogin() {
        val intent = GoogleLoginHelper.getLoginIntent()
        Log.d("GoogleLogin", "로그인 intent 실행됨")
        launcher.launch(intent)
    }
}
