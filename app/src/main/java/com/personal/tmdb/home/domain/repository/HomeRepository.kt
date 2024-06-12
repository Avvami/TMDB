package com.personal.tmdb.home.domain.repository

import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.core.util.TimeWindow
import com.personal.tmdb.core.domain.models.MediaInfo

interface HomeRepository {
    suspend fun getTrendingList(timeWindow: TimeWindow, language: String? = null): Resource<List<MediaInfo>>
}