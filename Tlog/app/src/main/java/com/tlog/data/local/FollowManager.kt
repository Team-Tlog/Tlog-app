package com.tlog.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.repository.SnsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "follow_list")

@Singleton
class FollowManager @Inject constructor(
    private val repository: SnsRepository,
    private val tokenProvider: TokenProvider,
    @ApplicationContext private val context: Context
) {
    private val FOLLOW_LIST = stringSetPreferencesKey("follow_list")

    private var userId: String? = null

    private var followList: Set<String> = emptySet()


    init {
        userId = tokenProvider.getUserId()
    }



    fun getFollowList() {
        repository
    }


    private suspend fun saveFollowList() {
        context.dataStore.edit { prefs ->
            prefs[FOLLOW_LIST] = followList
        }
    }



}