package com.tlog.viewmodel.share

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.LoginApi
import com.tlog.api.UserApi
import com.tlog.api.UserInfo
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.local.UserPreferences
import com.tlog.data.repository.MyPageRepository
import com.tlog.viewmodel.share.MyPageViewModel.UiEvent.LogoutSuccess
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject
import kotlin.toString

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val tokenProvider: TokenProvider,
    private val userPreferences: UserPreferences
): ViewModel() {
    private val _notification = mutableStateOf(true)
    val notification: State<Boolean> = _notification

    private val _userInfo: MutableState<UserInfo?> = mutableStateOf(null)
    val userInfo: State<UserInfo?> = _userInfo


    sealed class UiEvent {
        object LogoutSuccess: UiEvent()
        data class LogoutError(val message: String): UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val isGetUserApiSuccess = mutableStateOf(false)

    init {
        getUserInfo()
    }


    fun getUserInfo() {
        viewModelScope.launch {
            try {
                val result = myPageRepository.getUserInfo()

                when (result.status) {
                    200 -> {
                        _userInfo.value = result.data
                        isGetUserApiSuccess.value = true
                    }
                    else -> isGetUserApiSuccess.value = false
                }
            } catch (e: Exception) {
                Log.d("MyPageViewModel getUserInfo", e.message.toString())
            }
        }
    }

    // 로그아웃
    fun logout() {
        val refreshToken = tokenProvider.getRefreshToken() ?:""
        viewModelScope.launch {
            try {
                val result = myPageRepository.logout(refreshToken)
                when (result.status) {
                    200 -> {
                        _eventFlow.emit(LogoutSuccess)
                        userPreferences.clearTokens()
                    }

                    500 -> _eventFlow.emit(UiEvent.LogoutError("로그아웃 실패"))
                    else -> _eventFlow.emit(UiEvent.LogoutError("알 수 없는 오류가 발생했습니다."))
                }
            } catch (e: Exception) {
                Log.d("MyPageViewModel logout", e.message.toString())
            }
        }
    }

    fun changeNotification() {
        _notification.value = !_notification.value
    }
}

@Module
@InstallIn(SingletonComponent::class)
object MyPageRepositoryModule {
    @Provides
    fun provideMyPageRepository(
        loginApi: LoginApi,
        userApi: UserApi
    ): MyPageRepository {
        return MyPageRepository(loginApi, userApi)
    }

    @Provides
    fun provideLoginApi(
        retrofit: Retrofit
    ): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }
}