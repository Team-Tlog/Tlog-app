package com.tlog.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tlog.data.model.notification.NotificationItem
import com.tlog.data.model.notification.NotificationType
import com.tlog.data.model.notification.TSnsNotificationItem
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton


@Serializable
data class NotificationData(
    val notificationList: List<NotificationItem> = emptyList(),
    val tSnsNotificationList: List<TSnsNotificationItem> = emptyList()
)


@Singleton
class NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "notifications")
        private val NOTIFICATION_KEY = stringPreferencesKey("notification_data")
        private const val MAX_NOTIFICATION_COUNT = 50
    }

    suspend fun saveNotification(notification: NotificationItem) {
        dataStore.edit { preferences ->
            val currentData = getCurrentNotificationData(preferences)

            val updateList = buildList {
                add(notification)
                addAll(currentData.notificationList)

                if (size > MAX_NOTIFICATION_COUNT)
                    take(MAX_NOTIFICATION_COUNT)
            }

            val updateData = currentData.copy(
                notificationList = updateList
            )

            preferences[NOTIFICATION_KEY] = Json.encodeToString(updateData)

        }
    }

    suspend fun saveTSnsNotification(notification: TSnsNotificationItem) {
        dataStore.edit { preferences ->
            val currentData = getCurrentNotificationData(preferences)

            val updatedSnsList = buildList {
                add(notification)
                addAll(currentData.tSnsNotificationList)
                if (size > MAX_NOTIFICATION_COUNT) {
                    take(MAX_NOTIFICATION_COUNT)
                }
            }

            val updatedData = currentData.copy(
                tSnsNotificationList = updatedSnsList
            )

            preferences[NOTIFICATION_KEY] = Json.encodeToString(updatedData)
        }
    }

    // 일반 알림 조회
    fun getNotificationList(): Flow<List<NotificationItem>> {
        return dataStore.data.map { preferences ->
            getCurrentNotificationData(preferences).notificationList
        }
    }

    // SNS 알림 조회
    fun getTSnsNotificationList(): Flow<List<TSnsNotificationItem>> {
        return dataStore.data.map { preferences ->
            getCurrentNotificationData(preferences).tSnsNotificationList
        }
    }

    // SNS 알림 타입별 조회
    fun getTSnsNotificationsByType(type: NotificationType):
            Flow<List<TSnsNotificationItem>> {
        return getTSnsNotificationList().map { notifications ->
            notifications.filter { it.notificationType == type.type }
        }
    }

    private fun getCurrentNotificationData(preferences: Preferences):
            NotificationData {
        val json = preferences[NOTIFICATION_KEY] ?: ""
        return if (json.isNotEmpty()) {
            try {
                Json.decodeFromString<NotificationData>(json)
            } catch (e: Exception) {
                NotificationData()
            }
        } else {
            NotificationData()
        }
    }

    // 모든 알림 삭제
    suspend fun clearAllNotifications() {
        dataStore.edit { preferences ->
            preferences.remove(NOTIFICATION_KEY)
        }
    }
}