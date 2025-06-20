package com.tlog.viewmodel.tmp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.tlog.api.CourseApi
import com.tlog.data.repository.CourseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import retrofit2.Retrofit
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import com.tlog.data.api.UserCourseDestination
import com.tlog.data.local.UserPreferences

@HiltViewModel
class TmpCartViewModel @Inject constructor(
    private val repository: CourseRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {
    private var userId: String = ""

    private var _travelList = mutableStateOf<List<UserCourseDestination>>(emptyList())
    val travelList: State<List<UserCourseDestination>> get() = _travelList

    private var _checkedList = mutableStateOf<List<Boolean>>(emptyList())
    val checkedList: State<List<Boolean>> get() = _checkedList

    private var _allChecked = mutableStateOf(false)
    val allChecked: State<Boolean> get() = _allChecked

    init {
        fetchUserId()
    }

    private fun fetchUserId() {
        viewModelScope.launch {
            val id = userPreferences.getUserId().orEmpty()
            if (id.isNotEmpty()) {
                setUserId(id)
            }
        }
    }

    fun updateChecked(index: Int, newChecked: Boolean) {
        val updatedList = _checkedList.value.toMutableList()
        updatedList[index] = newChecked
        _checkedList.value = updatedList
    }

    fun allChecked() {
        _allChecked.value = !_allChecked.value
        _checkedList.value = List(_travelList.value.size) { _allChecked.value }
    }

    fun getCheckedTravelList(): List<UserCourseDestination> {
        return _travelList.value.filterIndexed { index, _ ->
            _checkedList.value.getOrNull(index) == true
        }
    }

    private fun setUserId(id: String) {
        userId = id
        fetchUserCourses()
    }

    private fun fetchUserCourses() {
        viewModelScope.launch {
            try {
                val response = repository.getUserCourses(userId)
                val destinations = response.data
                    ?.flatMap { it.dates }
                    ?.flatMap { it.destinationGroups }
                    ?.flatMap { it.destinations }
                    ?: emptyList()
                _travelList.value = destinations
                _checkedList.value = List(destinations.size) { false }
            } catch (e: Exception) {
                // TODO: Handle error
            }
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object CourseModule {
    @Provides
    fun provideCourseRepository(
        courseApi: CourseApi
    ): CourseRepository {
        return CourseRepository(courseApi)
    }

    @Provides
    fun provideCourseApi(
        retrofit: Retrofit
    ): CourseApi {
        return retrofit.create(CourseApi::class.java)
    }
}