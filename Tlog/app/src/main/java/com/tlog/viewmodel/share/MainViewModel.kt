package com.tlog.viewmodel.share

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.tlog.data.local.TokenProvider
import com.tlog.data.dto.share.LocationDataDto
import com.tlog.data.repository.MainRepository
import com.tlog.domain.model.share.Banner
import com.tlog.domain.model.share.LocalGuide
import com.tlog.domain.model.share.RecommendPost
import com.tlog.domain.model.share.RecommendTravels
import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    tokenProvider: TokenProvider,
    private val mainRepository: MainRepository
): BaseViewModel() {
    var userId: String? = null
    private val _banners = MutableStateFlow<List<Banner>>(emptyList())
    val banners: StateFlow<List<Banner>> = _banners.asStateFlow()

    private val _currentLocation = MutableStateFlow<LocationDataDto?>(null)
    val currentLocation: StateFlow<LocationDataDto?> = _currentLocation.asStateFlow()

    private val _localGuides = MutableStateFlow<List<LocalGuide>>(emptyList())
    val localGuides: StateFlow<List<LocalGuide>> = _localGuides.asStateFlow()

    private val _recommendPosts = MutableStateFlow<List<RecommendPost>>(emptyList())
    val recommendPosts: StateFlow<List<RecommendPost>> = _recommendPosts.asStateFlow()

    private val _recommendTravels = MutableStateFlow<List<RecommendTravels>>(emptyList())
    val recommendTravels: StateFlow<List<RecommendTravels>> = _recommendTravels.asStateFlow()

    init {
        userId = tokenProvider.getUserId()
    }

    fun getCurrentLocation(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            UiEvent.ShowToast("위치 권한이 없습니다")
            return
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location ->
            location?.let {
                val locationData = LocationDataDto(it.latitude, it.longitude)
                _currentLocation.value = locationData
                Log.d("MainViewModel", "위치 획득 성공: ${it.latitude}, ${it.longitude}")

                sendLocationToServer(locationData)
            } ?: run {
                UiEvent.ShowToast("위치를 가져올 수 없습니다")
            }
        }
    }

    fun getRecommendPosts() {
        launchSafeCall(
            action = {
               mainRepository.getRecommendPost()
            },
            onSuccess = { recommendPosts ->
                _recommendPosts.value = recommendPosts
            }
        )
    }

    fun getRecommendDestinations() {
        launchSafeCall(
            action = {
                mainRepository.getRecommendDestination()
            },
            onSuccess = { recommendTravels ->
                _recommendTravels.value = recommendTravels
            }
        )
    }

    private fun sendLocationToServer(location: LocationDataDto) {
        launchSafeCall(
            action = {
                mainRepository.getLocalGuide(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            },
            onSuccess = { localGuides ->
                _localGuides.value = localGuides
            }
        )
    }

    fun getRecommendBanner() {
        launchSafeCall(
            action = {
                mainRepository.getRecommendBanner()
            },
            onSuccess = { banners ->
                _banners.value = banners
            }
        )
    }

    fun navToTravel(travelId: String) {
        navigate(Screen.TravelInfo(travelId))
    }

    fun navToPost(postId: String) {
        navigate(Screen.SnsPostDetail(postId))
    }

    fun navToBannerDetail(bannerId: String) {
        navigate(Screen.BannerDetail(bannerId))
    }
}
