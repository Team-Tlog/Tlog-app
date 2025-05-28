package com.tlog.util

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

    // StateFlow to hold the current scrap list
    private val _scrapList = MutableStateFlow<List<String>>(emptyList())
    val scrapList: StateFlow<List<String>> = _scrapList

    // Call this to initialize or reload the scrap list from DataStore
    fun init(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val savedList = loadScrapList(context)
            _scrapList.value = savedList
        }
    }

    private suspend fun loadScrapList(context: Context): List<String> {
        return context.dataStore.data
            .map { preferences -> preferences[SCRAP_KEY] ?: emptySet() }
            .first()
            .toList()
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
    }

    private suspend fun saveScrapList(context: Context, list: List<String>) {
        context.dataStore.edit { prefs ->
            prefs[SCRAP_KEY] = list.toSet()
        }
    }

    suspend fun isScrapped(context: Context, id: String): Boolean {
        return scrapList.value.contains(id)
    }
}