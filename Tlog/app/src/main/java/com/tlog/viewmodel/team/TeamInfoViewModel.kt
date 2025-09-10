package com.tlog.viewmodel.team

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.CreateTeamRequest
import com.tlog.data.api.TravelPlan
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.repository.TeamRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import javax.inject.Inject
import kotlin.collections.set


@HiltViewModel
class TeamInfoViewModel @Inject constructor(
    private val repository: TeamRepository,
    tokenProvider: TokenProvider
) : ViewModel() {
    sealed interface UiEvent {
        data class Navigate(val target: Screen, val clearBackStack: Boolean = false): UiEvent
        data class PopBackStack(val count: Int = 1): UiEvent
        data class ShowToast(val message: String): UiEvent
    }

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()


    private var userId: String? = null

    init {
        userId = tokenProvider.getUserId()
    }

    private val _city = mutableStateOf("지역")
    val city: State<String> = _city

    private val _district = mutableStateOf("지역을 선택해주세요")
    val district: State<String> = _district

    private val _checkedDistrict = mutableStateOf<Set<String>>(emptySet())
    val checkedDistrict: State<Set<String>> = _checkedDistrict

    private val _hasPet = mutableStateOf(false)
    val hasPet: State<Boolean> = _hasPet

    private val _hasCar = mutableStateOf(false)
    val hasCar: State<Boolean> = _hasCar

    private val _startDate = mutableStateOf<LocalDate?>(null)
    val startDate: State<LocalDate?> = _startDate

    private val _endDate = mutableStateOf<LocalDate?>(null)
    val endDate: State<LocalDate?> = _endDate

    private val _travelCountByDate = mutableStateOf<Map<LocalDate, Int>>(emptyMap())
    val travelCountByDate: State<Map<LocalDate, Int>> = _travelCountByDate




    fun updateCheckedDistrict(district: String) {
        _checkedDistrict.value = _checkedDistrict.value + district
    }

    fun deleteCheckedDistrict(district: String) {
        _checkedDistrict.value = _checkedDistrict.value - district
    }

    fun updateCity(newCity: String) {
        _city.value = newCity
    }

    fun updateCar(newHasCar: Boolean) {
        _hasCar.value = newHasCar
    }

    fun updatePet(newHasPet: Boolean) {
        _hasPet.value = newHasPet
    }

    fun updateDateRange(clickedDate: LocalDate) {
        if (_startDate.value == null || _endDate.value != null) {
            _startDate.value = clickedDate
            _endDate.value = null
        } else {
            if (clickedDate.isBefore(_startDate.value)) {
                _endDate.value = _startDate.value
                _startDate.value = clickedDate
            } else
                _endDate.value = clickedDate
        }
    }

    fun getTravelDates(): List<LocalDate> {
        val start = startDate.value
        val end = endDate.value

        return if (start != null && end != null) {
            generateSequence(start) { it.plusDays(1) }
                .takeWhile { !it.isAfter(end) }
                .toList()
        } else emptyList()
    }

    fun updatePlaceCount(date: LocalDate, count: Int) {
        _travelCountByDate.value = _travelCountByDate.value.toMutableMap().apply {
            this[date] = count
        }
    }

    fun createTeam(teamName: String) {
        viewModelScope.launch {
            val safeUserId = userId ?: return@launch // null이면 launch 종료 (안돌아감)

            try {
                repository.createTeam(
                    CreateTeamRequest( //data에 팀아이디가 옴
                        name = teamName,
                        creator = safeUserId,
                        travelPlan = TravelPlan(
                            city = city.value,
                            regionList = checkedDistrict.value.toList(),
                            hasPet = hasPet.value,
                            hasTransport = hasCar.value,
                            startDate = startDate.value.toString(),
                            endDate = endDate.value.toString(),
                            visitCountPerDay = travelCountByDate.value.entries
                                .mapIndexed { index, value -> "${index + 1}" to value.value }
                                .toMap()
                        )
                    )
                )
                _uiEvent.trySend(UiEvent.PopBackStack(2))
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }
}