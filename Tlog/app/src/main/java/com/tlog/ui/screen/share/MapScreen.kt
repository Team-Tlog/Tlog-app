package com.tlog.ui.screen.share

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import java.lang.Exception


@Composable
fun MapScreen(

) {
    Box(modifier = Modifier.fillMaxSize()) {
        KakaoMapView()
    }
}


@Composable
fun KakaoMapView() {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            mapView.apply {
                mapView.start(
                    object : MapLifeCycleCallback() {
                        override fun onMapDestroy() {
                            Toast.makeText(context, "onMapDestroy", Toast.LENGTH_SHORT).show()
                            Log.d("MapLifeCycleCallback", "onMapDestroy")
                        }

                        override fun onMapError(p0: Exception?) {
                            Toast.makeText(context, "onMapError", Toast.LENGTH_SHORT).show()
                            Log.d("MapLifeCycleCallback", "onMapError")
                        }
                    },
                    object : KakaoMapReadyCallback() {
                        @SuppressLint("UseCompatLoadingForDrawables")
                        override fun onMapReady(kakaoMap: KakaoMap) {
                            val latLag = LatLng.from(37.5665, 126.9780)
                            kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(latLag, 2))
                        }
                    }
                )
            }
        }
    )
}
