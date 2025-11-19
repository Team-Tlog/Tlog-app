package com.tlog.viewmodel.travel

import androidx.lifecycle.ViewModel
import com.tlog.api.AiRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class CourseSharedViewModel @Inject constructor() : ViewModel() {
    private val _aiRequest = MutableStateFlow<AiRequest?>(null)
    val aiRequest: StateFlow<AiRequest?> = _aiRequest.asStateFlow()

    fun setAiRequest(request: AiRequest) {
        _aiRequest.value = request
    }
}