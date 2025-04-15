package com.tlog.viewmodel.api.beginning

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task

object GoogleLoginHelper {

    private lateinit var googleSignInClient: GoogleSignInClient

    // 초기화 - ApplicationContext에서 한 번만
    fun init(context: Context, webClientId: String) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)  // 웹 어플리케이션 클라이언트 ID 필요
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    // 로그인 intent 가져오기
    fun getLoginIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    // 로그인 결과로부터 Task<GoogleSignInAccount> 얻기
    fun getLoginAccountFromIntent(intent: Intent?): Task<GoogleSignInAccount> {
        return GoogleSignIn.getSignedInAccountFromIntent(intent)
    }

    // 로그아웃 (선택사항)
    fun signOut(context: Context) {
        googleSignInClient.signOut()
    }
}