package com.tlog.viewmodel.travel

import androidx.lifecycle.viewModelScope
import com.tlog.data.local.CourseIdManager
import com.tlog.data.model.travel.AiTravel
import com.tlog.data.model.travel.CourseDailySchedule
import com.tlog.data.repository.MyTravelingCourseRepository
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyTravelingCourseViewModel @Inject constructor(
    private val repository: MyTravelingCourseRepository,
    private val courseIdManager: CourseIdManager
) : BaseViewModel() {

    init {
        viewModelScope.launch {
            val courseId = courseIdManager.getCourseId()

            getCourse(courseId)
        }
    }
    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    private val _courses = MutableStateFlow<List<CourseDailySchedule>>(emptyList())
    val courses: StateFlow<List<CourseDailySchedule>> = _courses.asStateFlow()

    private val _uiTravels = MutableStateFlow<List<AiTravel>>(emptyList())
    val uiTravels: StateFlow<List<AiTravel>> = _uiTravels.asStateFlow()

    fun updateSelectedDay(idx: Int) {
        _selectedDay.value = idx

        updateUiTravels()
    }

    fun getCourse(courseId: String) {
        launchSafeCall(
            action = {
                repository.getCourse(courseId)
            },
            onSuccess = {
                _courses.value = it.data.dailySchedules

                updateUiTravels()
            }
        )
    }

    private fun updateUiTravels() {
        val dayIndex = _selectedDay.value - 1

        if (dayIndex in _courses.value.indices) {
            _uiTravels.value = _courses.value[dayIndex].groupedDestinations.values.flatten()
        } else {
            _uiTravels.value = emptyList()
        }
    }

    fun getDayCount(): Int {
        return _courses.value.size
    }


}