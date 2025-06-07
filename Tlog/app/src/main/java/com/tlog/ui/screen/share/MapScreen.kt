package com.tlog.ui.screen.share

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.tlog.ui.component.share.TopBar
import java.lang.Exception


@Composable
fun MapScreen(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        TopBar(
            text = "지도에서 보기"
        )
        KakaoMapView()
    }
}

@Composable
fun KakaoMapView() {
    val context = LocalContext.current
    Log.d("KakaoMapView", "KakaoMapView 시작") // 로그 추가

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            Log.d("KakaoMapView", "AndroidView factory 호출") // 로그 추가
            MapView(context).apply {
                Log.d("KakaoMapView", "MapView 생성 완료") // 로그 추가
                start(
                    object : MapLifeCycleCallback() {
                        override fun onMapDestroy() {
                            Log.d("MapLifeCycleCallback", "onMapDestroy")
                        }
                        override fun onMapError(exception: Exception?) {
                            Log.e("MapLifeCycleCallback", "onMapError: ${exception?.message}")
                        }
                    },
                    object : KakaoMapReadyCallback() {
                        override fun onMapReady(kakaoMap: KakaoMap) {
                            Log.d("MapLifeCycleCallback", "지도 준비됨")
                            val seoul = LatLng.from(37.5665, 126.9780)
                            kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(seoul))
                        }
                    }
                )
                Log.d("KakaoMapView", "MapView start 호출 완료") // 로그 추가
            }
        }
    )
}
