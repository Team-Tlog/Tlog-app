package com.tlog.viewmodel.travel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.tlog.api.RetrofitInstance
import com.tlog.api.TravelApi
import com.tlog.data.model.Location
import com.tlog.data.model.travel.Travel
import com.tlog.data.repository.AddTravelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
class AddTravelViewModel @Inject constructor(
    private val repository: AddTravelRepository
): ViewModel() {

    suspend fun addTravel(travel: Travel) {
        repository.addTravel(travel)
    }

    private var _travelName = mutableStateOf("")
    val travelName: State<String> = _travelName

    private var _travelAddress = mutableStateOf("")
    val travelAddress: State<String> = _travelAddress

    private var _hasParking = mutableStateOf(false)
    val hasParking: State<Boolean> = _hasParking

    private var _isPetFriendly = mutableStateOf(false)
    val isPetFriendly: State<Boolean> = _isPetFriendly

    private var _hashTag = mutableStateOf("")
    val hashTag: State<String> = _hashTag

    private var _hashTags =
        mutableStateOf<List<String>>(emptyList()) // 테스트용 2개 추후 로직 생성 시 삭제할 것
    val hashTags: State<List<String>> = _hashTags

    private var _travelDescription = mutableStateOf("")
    val travelDescription: State<String> = _travelDescription

    private var _imageUri = mutableStateOf(Uri.EMPTY)
    val imageUri: State<Uri> = _imageUri




    fun addNewTravel() {
        viewModelScope.launch {
            try {
                addTravel(
                    Travel(
                        name = travelName.value,
                        address = travelAddress.value,
                        location = Location("0.0", "0.0"),
                        city = "임시", // 시, 도
                        district = "임시", // 시, 군, 구
                        hasParking = hasParking.value,
                        petFriendly = isPetFriendly.value,
                        imageUri = imageUri.value.toString(),
                        description = travelDescription.value,
                        customTags = hashTags.value
                    )
                )
            }
            catch (e: HttpException) {
                Log.e("AddTravel", "서버 오류: ${e.code()} - ${e.message()}")
            } catch (e: Exception) {
                Log.e("AddTravel", "알 수 없는 오류", e)
            }
        }
    }


    fun updateTravelName(newTravelName: String) {
        _travelName.value = newTravelName
    }

    fun updateTravelAddress(newTravelAddress: String) {
        _travelAddress.value = newTravelAddress
    }

    fun updateHasParking(newHasParking: Boolean) {
        _hasParking.value = newHasParking
    }

    fun updateIsPetFriendly(newIsPetFreindlyName: Boolean) {
        _isPetFriendly.value = newIsPetFreindlyName
    }

    fun updateTravelDescription(newTravelDescription: String) {
        _travelDescription.value = newTravelDescription
    }

    fun updateHashTag(newHashTag: String) {
        _hashTag.value = newHashTag
    }

    fun addHashTag(hashTag: String) {
        _hashTags.value += hashTag
    }

    fun clearHashTags() {
        _hashTags.value = emptyList()
    }

    fun addImage(uri: Uri) {
        _imageUri.value = uri
    }

    fun clearImages() {
        _imageUri.value = Uri.EMPTY
    }

    fun checkInput(): Boolean {
        if (travelName.value.isEmpty() || travelAddress.value.isEmpty() || travelDescription.value.isEmpty())
            return false
        return true
    }
}

@Module
@InstallIn(SingletonComponent::class)
object TravelModule {
    @Provides
    fun provideTravelApi(): TravelApi {
        return RetrofitInstance.getInstance().create(TravelApi::class.java)
    }

    @Provides
    fun provideAddTravelRepository(
        travelApi: TravelApi
    ): AddTravelRepository {
        return AddTravelRepository(travelApi)
    }
}