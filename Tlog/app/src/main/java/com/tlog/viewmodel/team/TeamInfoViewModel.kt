package com.tlog.viewmodel.team

import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.local.TokenProvider
import com.tlog.data.model.request.team.CreateTeamRequest
import com.tlog.data.model.request.team.TravelPlanBody
import com.tlog.data.local.RegionCode
import com.tlog.data.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject
import kotlin.collections.set


@HiltViewModel
class TeamInfoViewModel @Inject constructor(
    private val repository: TeamRepository,
    tokenProvider: TokenProvider
) : BaseViewModel() {
    private var userId: String? = null

    private val _city = MutableStateFlow("지역")
    val city = _city.asStateFlow()

    private val _district = MutableStateFlow("지역을 선택해주세요")
    val district = _district.asStateFlow()

    private val _checkedDistrict = MutableStateFlow<Set<String>>(emptySet())
    val checkedDistrict = _checkedDistrict.asStateFlow()

    private val _hasPet = MutableStateFlow(false)
    val hasPet = _hasPet.asStateFlow()

    private val _hasCar = MutableStateFlow(false)
    val hasCar = _hasCar.asStateFlow()

    private val _startDate = MutableStateFlow<LocalDate?>(null)
    val startDate = _startDate.asStateFlow()

    private val _endDate = MutableStateFlow<LocalDate?>(null)
    val endDate = _endDate.asStateFlow()

    private val _travelCountByDate = MutableStateFlow<Map<LocalDate, Int>>(emptyMap())
    val travelCountByDate = _travelCountByDate.asStateFlow()

    init {
        userId = tokenProvider.getUserId()
    }

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
        val safeUserId = userId ?: return // null이면 return

        launchSafeCall(
            action = {
                repository.createTeam(
                    CreateTeamRequest( //data에 팀아이디가 옴
                        name = teamName,
                        creator = safeUserId,
                        travelPlan = TravelPlanBody(
                            city = city.value,
                            regionList = checkedDistrict.value.map {
                                RegionCode.fromStringOrNull(it).toString()
                            },
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
                showToast("팀이 생성되었습니다")
                popBackStack(2)
            }
        )
    }
}