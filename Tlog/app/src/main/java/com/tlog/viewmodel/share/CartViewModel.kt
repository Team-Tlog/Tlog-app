package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.TravelApi
import com.tlog.api.UserApi
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.ScrapDestinationResponse
import com.tlog.data.model.travel.Travel
import com.tlog.data.repository.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository,
    private val tokenProvider: TokenProvider
): ViewModel() {


    var userId: String = ""

    fun fetchCart(userId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getUserCart(userId)
                _cartList.value = result
            } catch (e: Exception) {
                // api실패 시
            }
        }
    }

    fun initUserIdAndCart() {
        userId = tokenProvider.getUserId()?: ""
        fetchCart(userId)
    }


    private var _cartList = mutableStateOf<List<Travel>>(emptyList())
    val cartList: State<List<Travel>> = _cartList

    private var _scrapList = mutableStateOf<List<ScrapDestinationResponse>>(emptyList())
    val scrapList: State<List<ScrapDestinationResponse>> = _scrapList

    fun fetchScrapList(userId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getUserScrap(userId)
                _scrapList.value = result
            } catch (e: Exception) {
                // api실패 시
            }
        }
    }

    private var _checkedTravelList = mutableStateOf<List<String>>(emptyList())
    val checkedTravelList: State<List<String>> = _checkedTravelList

    fun updateCheckedTravelList(travelName: String) {
        if (_checkedTravelList.value.contains(travelName))
            _checkedTravelList.value -= travelName
        else
            _checkedTravelList.value += travelName
    }

    fun isChecked(travelName: String): Boolean {
        return _checkedTravelList.value.contains(travelName)
    }

    fun allChecked() {
        if (_checkedTravelList.value.size != _cartList.value.size)
            _checkedTravelList.value = _cartList.value.map { it.name }
        else
            _checkedTravelList.value = emptyList()
    }
}


@Module
@InstallIn(SingletonComponent::class)
object CartModule {
    @Provides
    fun provideCartRepository(
        userApi: UserApi,
        travelApi: TravelApi
    ): CartRepository {
        return CartRepository(userApi, travelApi)
    }

    @Provides
    fun provideUserApi(
        retrofit: Retrofit
    ): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    fun provideTravelApi(
        retrofit: Retrofit
    ): TravelApi {
        return retrofit.create(TravelApi::class.java)
    }
}