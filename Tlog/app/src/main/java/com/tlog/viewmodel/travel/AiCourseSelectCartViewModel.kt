package com.tlog.viewmodel.travel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.tlog.api.AiRequest
import com.tlog.api.AiTravel
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.model.travel.Cart
import com.tlog.data.repository.AiCourseSelectCartRepository
import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.collections.minus
import kotlin.collections.plus


@HiltViewModel
class AiCourseSelectCartViewModel @Inject constructor(
    val repository: AiCourseSelectCartRepository,
    val tokenProvider: TokenProvider
) : BaseViewModel() {
    var userId: String? = null

    private var _cartList = mutableStateOf<List<Cart>>(emptyList())
    val cartList: State<List<Cart>> = _cartList

    private val _checkedTravelList = mutableStateOf<List<String>>(emptyList())
    val checkedTravelList: State<List<String>> = _checkedTravelList

    private val _aiTravelMap = MutableStateFlow<Map<String, List<AiTravel>>>(emptyMap())
    val aiTravelMap: StateFlow<Map<String, List<AiTravel>>> = _aiTravelMap


    init {
        userId = tokenProvider.getUserId()

        if (userId != null) {
            fetchCart(userId!!)
        }
    }

    fun getAiCourse(aiRequest: AiRequest) {
        launchSafeCall(
            action = {
                val response = repository.getAiCourseRecommendations(
                    ownerId = userId!!,
                    ownerType = "USER",
                    aiRequest = aiRequest.copy(
                        wishlist = _cartList.value.filter {
                            _checkedTravelList.value.contains(it.name)
                        }
                    )
                )

                _aiTravelMap.value = response.data

                Log.d("AiCourse", _aiTravelMap.value.toString())
            }
        )
    }

    private fun fetchCart(userId: String) {
        launchSafeCall(
            action = {
                _cartList.value = repository.getUserCart(userId)
            }
        )
    }

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
        val allItems = cartList.value.map { it.name }

        if (_checkedTravelList.value.size != allItems.size)
            _checkedTravelList.value = allItems
        else
            clearChecked()
    }

    private fun clearChecked() {
        _checkedTravelList.value = emptyList()
    }

    fun navToAiCourseResult() {
        navigate(Screen.AiCourseResult)
    }

}