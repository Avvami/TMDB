package com.personal.tmdb.core.data.remote

import com.personal.tmdb.BuildConfig
import com.personal.tmdb.core.util.TimeWindow
import com.personal.tmdb.core.data.models.MediaDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/trending/all/{time_window}?")
    suspend fun getTrending(
        @Path("time_window") timeWindow: TimeWindow,
        @Query("language") language: String?
    ): MediaDto
}