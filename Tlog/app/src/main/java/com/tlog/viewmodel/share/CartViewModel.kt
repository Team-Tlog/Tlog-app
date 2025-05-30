package com.tlog.viewmodel.share

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.RetrofitInstance
import com.tlog.api.UserApi
import com.tlog.data.local.UserPreferences
import com.tlog.data.model.travel.Travel
import com.tlog.data.repository.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
): ViewModel() {


    private var userId: String = ""

//    init {
//        fetchCart(userId)
//    }
    private fun fetchCart(userId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getUserCart(userId)
                _cartList.value = result ?: emptyList() // null이면 emptyList
            } catch (e: Exception) {
                // api실패 시
            }
        }
    }


    fun initUserIdAndCart(context: Context) {
        viewModelScope.launch {
            userId = UserPreferences.getUserId(context)?: ""
        }
        fetchCart(userId)
    }




    private var _cartList = mutableStateOf<List<Travel>>(emptyList())
    val cartList: State<List<Travel>> = _cartList

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
        userApi: UserApi
    ): CartRepository {
        return CartRepository(userApi)
    }

    @Provides
    fun provideUserApi(): UserApi {
        return RetrofitInstance.getInstance().create(UserApi::class.java)
    }
}