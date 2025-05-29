package com.tlog.data.local

import com.tlog.data.repository.RecommendDestinationRepository

import android.util.Log
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore(name = "scrap_prefs")

object ScrapManager {
    private val SCRAP_KEY = stringSetPreferencesKey("scrap_list")

    private val _scrapList = MutableStateFlow<List<String>>(emptyList())
    val scrapList: StateFlow<List<String>> = _scrapList


    fun init(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val savedList = loadScrapList(context)
            _scrapList.value = savedList
        }
    }

    private suspend fun loadScrapList(context: Context): List<String> {
        val preferences = context.dataStore.data
            .first()
        Log.d("ScrapManager", "DataStore에서 불러온 scrapList: ${preferences[SCRAP_KEY] ?: emptySet()}")
        return preferences[SCRAP_KEY]?.toList() ?: emptyList()
    }

    suspend fun toggleScrap(context: Context, id: String) {
        val currentList = _scrapList.value.toMutableList()
        if (currentList.contains(id)) {
            currentList.remove(id)
        } else {
            currentList.add(id)
        }
        _scrapList.value = currentList
        saveScrapList(context, currentList)
        Log.d("ScrapManager", "스크랩 추가됨: $id")
    }

    private suspend fun saveScrapList(context: Context, list: List<String>) {
        context.dataStore.edit { prefs ->
            prefs[SCRAP_KEY] = list.toSet()
        }
        Log.d("ScrapManager", "DataStore에 저장된 scrapList: ${list.toSet()}")
    }

    fun removeScrap(context: Context, destinationId: String) {
        val currentList = _scrapList.value.toMutableList()
        if (currentList.remove(destinationId)) {
            _scrapList.value = currentList
            CoroutineScope(Dispatchers.IO).launch {
                saveScrapList(context, currentList)
            }
            Log.d("ScrapManager", "스크랩 제거됨: $destinationId")
        }
    }

    suspend fun refreshScrapList(context: Context, userId: String, repository: RecommendDestinationRepository) {
        try {
            val response = repository.getUserScraps(userId)
            val destinationIds = response.data?.map { it.id } ?: emptyList()
            _scrapList.value = destinationIds
            Log.d("ScrapManager", "리프레시된 scrapList: $destinationIds")
            saveScrapList(context, destinationIds)
            Log.d("ScrapManager", "스크랩 목록 갱신됨: $destinationIds")
        } catch (e: Exception) {
            Log.e("ScrapManager", "스크랩 목록 갱신 실패", e)
        }
    }
}