package com.personal.tmdb.core.domain.repository

interface LocalCache {

    fun saveRequestToken(token: String)

    fun getRequestToken(): String

    fun clearRequestToken()
}