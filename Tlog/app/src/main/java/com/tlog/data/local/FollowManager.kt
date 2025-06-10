package com.tlog.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.repository.SnsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "follow_list")

@Singleton
class FollowManager @Inject constructor(
    private val repository: SnsRepository,
    tokenProvider: TokenProvider,
    @ApplicationContext private val context: Context
) {
    private val FOLLOW_LIST = stringSetPreferencesKey("follow_list")

    private var userId: String? = null

    private val _followingList = MutableStateFlow<Set<String>>(emptySet())
    val followingList: StateFlow<Set<String>> get() = _followingList


    init {
        userId = tokenProvider.getUserId()
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.data.collect { preferences ->
                _followingList.value = preferences[FOLLOW_LIST] ?: emptySet()
                if (_followingList.value.isEmpty()) {
                    getFollowingList()
                }
            }
        }
    }

    suspend fun getFollowingList() {
       try {
           val result = repository.getFollowingList(userId!!)
           _followingList.value = result.data.map { it.uuid }.toSet()
           saveFollowList()
       } catch (e: Exception) {
           Log.d("FollowManager", e.message.toString())
       }
    }


    private suspend fun saveFollowList() {
        context.dataStore.edit { preferences ->
            preferences[FOLLOW_LIST] = followingList.value
        }
    }

    fun getIsFollowing(userId: String): Boolean {
        return followingList.value.contains(userId)
    }

    fun followUser(toUserId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.followUser(userId!!, toUserId)
            getFollowingList()
            saveFollowList()
        }
    }


}