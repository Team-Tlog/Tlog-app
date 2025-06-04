package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.TravelApi
import com.tlog.api.UserApi
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.ScrapDestinationResponse
import com.tlog.data.model.travel.ShopCart
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

    fun initUserIdAndScrapList() {
        userId = tokenProvider.getUserId()?: ""
        fetchScrapList(userId)
    }


    private var _cartList = mutableStateOf<List<ShopCart>>(emptyList())
    val cartList: State<List<ShopCart>> = _cartList

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

    fun clearChecked() {
        _checkedTravelList.value = emptyList()
    }

    fun deleteSelectedItems(selectedTab: String) {
        viewModelScope.launch {
            try {
                checkedTravelList.value.forEach { destName ->
                    if (selectedTab == "스크랩") {
                        val destinationId = scrapList.value.find { it.name == destName }?.id ?: return@forEach
                        repository.deleteScrapDestination(userId, destinationId)
                    } else {
                        val destinationId = cartList.value.find { it.name == destName }?.id ?: return@forEach
                        repository.deleteTravelFromCart(userId, destinationId)
                    }
                }
                clearChecked()
                if (selectedTab == "스크랩") {
                    fetchScrapList(userId)
                } else {
                    fetchCart(userId)
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }


    fun addSelectedTravelToCart() {
        viewModelScope.launch {
            try {
                checkedTravelList.value.forEach { destName ->
                    val destinationId = scrapList.value.find { it.name == destName }?.id ?: return@forEach
                    repository.addDestinationToCart(userId, destinationId)
                }
                clearChecked()
                fetchCart(userId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }


    fun allChecked(selectedTab: String) {
        val allItems = if (selectedTab == "스크랩") {
            scrapList.value.map { it.name }
        } else {
            cartList.value.map { it.name }
        }
        if (_checkedTravelList.value.size != allItems.size)
            _checkedTravelList.value = allItems
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
}