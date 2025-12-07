package com.tlog.viewmodel.share

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.tlog.data.local.TokenProvider
import com.tlog.data.dto.share.BannerDto
import com.tlog.data.dto.share.LocationDataDto
import com.tlog.data.dto.share.PostDto
import com.tlog.data.dto.share.RecommendDestinationDto
import com.tlog.data.repository.MainRepository
import com.tlog.domain.model.share.LocalGuide
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
    private val _bannerList = MutableStateFlow<List<BannerDto>>(emptyList())
    val bannerList: StateFlow<List<BannerDto>> = _bannerList.asStateFlow()

    private val _currentLocation = MutableStateFlow<LocationDataDto?>(null)
    val currentLocation: StateFlow<LocationDataDto?> = _currentLocation.asStateFlow()

    private val _localGuides = MutableStateFlow<List<LocalGuide>>(emptyList())
    val localGuides: StateFlow<List<LocalGuide>> = _localGuides.asStateFlow()

    private val _recommendPosts = MutableStateFlow<List<PostDto>>(emptyList())
    val recommendPosts: StateFlow<List<PostDto>> = _recommendPosts.asStateFlow()

    private val _recommendDestinations = MutableStateFlow<List<RecommendDestinationDto>>(emptyList())
    val recommendDestinations: StateFlow<List<RecommendDestinationDto>> = _recommendDestinations.asStateFlow()

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
                val response = mainRepository.getRecommendPost()

                _recommendPosts.value = response.data
            }
        )
    }

    fun getRecommendDestinations() {
        launchSafeCall(
            action = {
                val response = mainRepository.getRecommendDestination()

                _recommendDestinations.value = response.data
            }
        )
    }

    private fun sendLocationToServer(location: LocationDataDto) {
        launchSafeCall(
            action = {
                val response = mainRepository.getLocalGuide(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
                _localGuides.value = response
            }
        )
    }

    fun getRecommendBanner() {
        launchSafeCall(
            action = {
                val response = mainRepository.getRecommendBanner()

                _bannerList.value = response.data
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
