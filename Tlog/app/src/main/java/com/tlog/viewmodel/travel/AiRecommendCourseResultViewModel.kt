package com.tlog.viewmodel.travel

import androidx.lifecycle.viewModelScope
import com.tlog.data.local.TokenProvider
import com.tlog.data.local.CourseIdManager
import com.tlog.data.model.travel.AiTravel
import com.tlog.data.model.travel.CourseSaveRequest
import com.tlog.data.model.travel.DailySchedule
import com.tlog.data.repository.AiRecommendCourseResultRepository
import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate


@HiltViewModel
class AiRecommendCourseResultViewModel @Inject constructor(
    val repository: AiRecommendCourseResultRepository,
    val tokenProvider: TokenProvider,
    val courseManager: CourseIdManager
) : BaseViewModel() {
    private var userId = ""
    private var startDate = ""
    private var endDate = ""

    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    private val _aiTravelMap = MutableStateFlow<Map<String, List<AiTravel>>>(emptyMap())
    val aiTravelMap: StateFlow<Map<String, List<AiTravel>>> = _aiTravelMap

    private val _dayOfCount = MutableStateFlow<List<Int>>(emptyList())
    val dayOfCount: StateFlow<List<Int>> = _dayOfCount.asStateFlow()

    private val _dailyTravels = MutableStateFlow<List<List<AiTravel>>>(emptyList())
    val dailyTravels: StateFlow<List<List<AiTravel>>> = _dailyTravels.asStateFlow()

    private val _uiTravels = MutableStateFlow<List<AiTravel>>(emptyList())
    val uiTravels: StateFlow<List<AiTravel>> = _uiTravels.asStateFlow()

    init {
        userId = tokenProvider.getUserId() ?: ""
    }

    fun updateSelectedDay(idx: Int) {
        _selectedDay.value = idx

        updateUiTravels()
    }

    fun setAiTravelMap(
        map: Map<String, List<AiTravel>>,
        dayOfCount: List<Int>,
        startDate: String,
        endDate: String,
    ) {
        _aiTravelMap.value = map
        _dayOfCount.value = dayOfCount

        val flatList = map.values.flatten()

        val split = mutableListOf<List<AiTravel>>()
        var index = 0

        dayOfCount.forEach { count ->
            val end = index + count
            if (index < flatList.size) {
                split.add(flatList.subList(index, minOf(end, flatList.size)))
            } else {
                split.add(emptyList())
            }
            index = end
        }

        this.startDate = startDate
        this.endDate = endDate

        _dailyTravels.value = split

        updateUiTravels()
    }

    fun deleteTravelByName(name: String) {
        val updatedUiList = _uiTravels.value.filter { it.name != name }
        _uiTravels.value = updatedUiList

        val dayIndex = _selectedDay.value - 1
        if (dayIndex in _dailyTravels.value.indices) {
            val updatedDailyList = _dailyTravels.value.toMutableList()
            updatedDailyList[dayIndex] = updatedUiList
            _dailyTravels.value = updatedDailyList
        }
    }

    fun getDayCount(): Int {
        return _dayOfCount.value.size
    }

    private fun updateUiTravels() {
        val dayIndex = _selectedDay.value - 1

        if (dayIndex in _dailyTravels.value.indices) {
            _uiTravels.value = _dailyTravels.value[dayIndex]
        } else {
            _uiTravels.value = emptyList()
        }
    }

    fun saveCourse(isTeam: Boolean, teamId: String = "") {
        val targetDate = LocalDate.parse(startDate)
        val dailySchedules = List(getDayCount()) { i ->
            DailySchedule(
                dayNumber = i,
                date = targetDate.plusDays(i.toLong()).toString(),
                destinationIds = _dailyTravels.value[i].map { it.id }
            )
        }

        val courseSave = CourseSaveRequest(
            startDate = startDate,
            endDate = endDate,
            dailySchedules = dailySchedules
        )


        launchSafeCall(
            action = {
                repository.saveCourse(
                    ownerId = if (isTeam) teamId else userId,
                    ownerType = if (isTeam) "TEAM" else "USER",
                    courseSaveRequest = courseSave
                )
            },
            onSuccess = {
                viewModelScope.launch {
                    courseManager.saveCourseId(it.data)
                }
                showToast("코스가 저장되었습니다.")
                navToMain()
            }
        )
    }

    private fun navToMain() {
        navigate(target = Screen.Main, clearBackStack = true)
    }
}
