package com.tlog.viewmodel.travel

import com.tlog.data.local.TokenProvider
import com.tlog.data.model.request.travel.AiRequest
import com.tlog.data.model.response.travel.AiTravel
import com.tlog.data.model.travel.Cart
import com.tlog.data.repository.AiCourseSelectCartRepository
import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.collections.minus
import kotlin.collections.plus


@HiltViewModel
class AiCourseSelectCartViewModel @Inject constructor(
    val repository: AiCourseSelectCartRepository,
    val tokenProvider: TokenProvider
) : BaseViewModel() {
    var userId: String? = null

    private var _cartList = MutableStateFlow<List<Cart>>(emptyList())
    val cartList = _cartList.asStateFlow()

    private val _aiTravelMap = MutableStateFlow<Map<String, List<AiTravel>>>(emptyMap())
    val aiTravelMap: StateFlow<Map<String, List<AiTravel>>> = _aiTravelMap

    private val _checkedTravelList = MutableStateFlow<List<String>>(emptyList())
    val checkedTravelList = _checkedTravelList.asStateFlow()


    init {
        userId = tokenProvider.getUserId()

        if (userId != null) {
            fetchCart(userId!!)
        }
    }

    fun getAiCourse(aiRequest: AiRequest, isTeam: Boolean, teamId: String = "") {
        launchSafeCall(
            action = {
                val response = repository.getAiCourseRecommendations(
                    ownerId = if (isTeam) teamId else userId!!,
                    ownerType = if (isTeam) "TEAM" else "USER",
                    aiRequest = aiRequest.copy(
                        wishlist = _cartList.value.filter {
                            _checkedTravelList.value.contains(it.name)
                        }
                    )
                )

                _aiTravelMap.value = response.data
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

    fun navToAiCourseResult(isTeam: Boolean) {
        navigate(Screen.AiCourseResult(isTeam))
    }

    fun setCheckedList(names: List<String>) {
        _checkedTravelList.value = names
    }
}
