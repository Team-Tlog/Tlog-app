package com.tlog.data.local


import android.util.Log
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tlog.api.ScrapApi
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.repository.ScrapRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "scrap_prefs")

@Singleton
class ScrapManager @Inject constructor(
    private val repository: ScrapRepository,
    private val tokenProvider: TokenProvider,
    @ApplicationContext private val context: Context
){
    private val SCRAP_KEY = stringSetPreferencesKey("scrap_list")

    private val _scrapList = mutableStateOf<List<String>>(emptyList())
    val scrapList: State<List<String>> = _scrapList

    private var userId: String? = null

    fun init() {
        userId = tokenProvider.getUserId()
        CoroutineScope(Dispatchers.IO).launch {
            val savedList = loadScrapList()
            _scrapList.value = savedList
        }
    }

    private suspend fun loadScrapList(): List<String> {
        val preferences = context.dataStore.data.first()
        Log.d("ScrapManager", "DataStore에서 불러온 scrapList: ${preferences[SCRAP_KEY] ?: emptySet()}")
        return preferences[SCRAP_KEY]?.toList() ?: emptyList()
    }

    suspend fun toggleScrap(destinationId: String) {
        val currentList = _scrapList.value.toMutableList()
        if (currentList.contains(destinationId)) {
            repository.deleteScrapDestination(userId!!, destinationId)
            Log.d("ScrapManager", "스크랩 제거됨: $destinationId")
        } else {
            repository.scrapDestination(userId!!, destinationId)
            Log.d("ScrapManager", "스크랩 추가됨: $destinationId")
        }

        _scrapList.value = repository.getUserScraps(userId!!).data.map { it.id }

        saveScrapList(currentList)
    }

    private suspend fun saveScrapList(list: List<String>) {
        context.dataStore.edit { prefs ->
            prefs[SCRAP_KEY] = list.toSet()
        }
        Log.d("ScrapManager", "DataStore에 저장된 scrapList: ${list.toSet()}")
    }

    fun removeScrap(destinationId: String) {
        val currentList = _scrapList.value.toMutableList()
        if (currentList.remove(destinationId)) {
            _scrapList.value = currentList
            CoroutineScope(Dispatchers.IO).launch {
                saveScrapList(currentList)
            }
            Log.d("ScrapManager", "스크랩 제거됨: $destinationId")
        }
    }

    suspend fun refreshScrapList(userId: String) {
        try {
            val response = repository.getUserScraps(userId)
            val destinationIds = response.data?.map { it.id } ?: emptyList()
            _scrapList.value = destinationIds
            Log.d("ScrapManager", "리프레시된 scrapList: $destinationIds")
            saveScrapList(destinationIds)
            Log.d("ScrapManager", "스크랩 목록 갱신됨: $destinationIds")
        } catch (e: Exception) {
            Log.e("ScrapManager", "스크랩 목록 갱신 실패", e)
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object ScrapModule {
    @Provides
    fun provideScrapApi(
        retrofit: Retrofit
    ): ScrapApi {
        return retrofit.create(ScrapApi::class.java)
    }

    @Provides
    fun provideScrapRepository(
        scrapApi: ScrapApi
    ): ScrapRepository {
        return ScrapRepository(scrapApi)
    }
}