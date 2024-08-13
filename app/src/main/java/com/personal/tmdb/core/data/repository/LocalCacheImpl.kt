package com.personal.tmdb.core.data.repository

import android.content.SharedPreferences
import com.personal.tmdb.core.domain.repository.LocalCache
import javax.inject.Inject

class LocalCacheImpl @Inject constructor(
    private val preferences: SharedPreferences
): LocalCache {
    override fun saveRequestToken(token: String) {
        preferences.edit()
            .putString("requestToken", token)
            .apply()
    }

    override fun getRequestToken(): String {
        return preferences.getString("requestToken", "") ?: ""
    }

    override fun clearRequestToken() {
        preferences.edit()
            .remove("requestToken")
            .apply()
    }
}