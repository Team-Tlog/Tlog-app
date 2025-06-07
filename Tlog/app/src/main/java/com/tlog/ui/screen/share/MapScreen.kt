package com.tlog.ui.screen.share

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.label.LabelTextBuilder
import com.tlog.R
import com.tlog.data.api.ScrapDestinationResponse
import com.tlog.data.model.travel.ShopCart
import com.tlog.ui.component.share.TopBar
import com.tlog.viewmodel.share.MapViewModel
import java.lang.Exception


@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        TopBar(
            text = "지도에서 보기"
        )
        KakaoMapView(
            cartList = viewModel.cartList.value,
            scrapList = viewModel.scrapList.value
        )
    }
}

@Composable
fun KakaoMapView(
    cartList: List<ShopCart>,
    scrapList: List<ScrapDestinationResponse>
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            MapView(context).apply {
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
                            val seoul = LatLng.from(37.5665, 126.9780)
                            kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(seoul))

                            // 카트랑 스크랩 마커 찍는 부분
                            cartList.forEach { cart ->
                                val latitude = cart.location.latitude
                                val longitude = cart.location.longitude
                                val latLng = LatLng.from(latitude.toDouble(), longitude.toDouble())

                                val style = LabelStyles.from(
                                    LabelStyle.from(R.drawable.map_pin_cart)                                )

                                val options = LabelOptions.from(latLng)
                                    .setTexts(LabelTextBuilder().setTexts(cart.name))
                                    .setStyles(style)

                                kakaoMap.labelManager?.layer?.addLabel(options)
                            }

                            scrapList.forEach { scrap ->
                                val latitude = scrap.location.latitude
                                val longitude = scrap.location.longitude
                                val latLng = LatLng.from(latitude.toDouble(), longitude.toDouble())

                                val style = LabelStyles.from(
                                    LabelStyle.from(R.drawable.map_pin_cart)                                )

                                val options = LabelOptions.from(latLng)
                                    .setTexts(LabelTextBuilder().setTexts(scrap.name))
                                    .setStyles(style)

                                kakaoMap.labelManager?.layer?.addLabel(options)
                            }
                        }
                    }
                )
            }
        }
    )
}
