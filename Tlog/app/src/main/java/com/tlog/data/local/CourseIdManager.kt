package com.tlog.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

private val Context.courseIdDataStore by preferencesDataStore(name = "course_id")

@Singleton
class CourseIdManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val COURSE_ID = stringPreferencesKey("recent_searches")

    // 최근 검색어 저장 (쉼표로 구분)
    suspend fun saveCourseId(courseId: String) {
        context.courseIdDataStore.edit { preferences ->
            preferences[COURSE_ID] = courseId
        }
    }

    suspend fun getCourseId(): String {
        val prefs = context.courseIdDataStore.data.first()
        val courseId = prefs[COURSE_ID] ?: "코스 아이디 없음"

        return courseId
    }


    suspend fun clearCourseId() {
        context.courseIdDataStore.edit { preferences ->
            preferences.remove(COURSE_ID)
        }
    }
}