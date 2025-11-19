package com.tlog.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.recentSearchDataStore by preferencesDataStore(name = "recent_search")

@Singleton
class RecentSearchPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val RECENT_SEARCHES = stringPreferencesKey("recent_searches")

    // 최근 검색어 저장 (쉼표로 구분)
    suspend fun saveRecentSearches(searches: List<String>) {
        context.recentSearchDataStore.edit { preferences ->
            preferences[RECENT_SEARCHES] = searches.joinToString(",")
        }
    }

    // 최근 검색어 가져오기
    suspend fun getRecentSearches(): List<String> {
        val prefs = context.recentSearchDataStore.data.first()
        val searchesString = prefs[RECENT_SEARCHES] ?: ""
        return if (searchesString.isNotEmpty()) {
            searchesString.split(",")
        } else {
            emptyList()
        }
    }

    // 최근 검색어 Flow로 가져오기 (실시간 업데이트)
    fun getRecentSearchesFlow(): Flow<List<String>> {
        return context.recentSearchDataStore.data.map { preferences ->
            val searchesString = preferences[RECENT_SEARCHES] ?: ""
            if (searchesString.isNotEmpty()) {
                searchesString.split(",")
            } else {
                emptyList()
            }
        }
    }

    // 최근 검색어 전체 삭제
    suspend fun clearRecentSearches() {
        context.recentSearchDataStore.edit { preferences ->
            preferences.remove(RECENT_SEARCHES)
        }
    }
}