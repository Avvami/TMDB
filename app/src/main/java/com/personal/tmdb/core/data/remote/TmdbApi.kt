package com.personal.tmdb.core.data.remote

import com.personal.tmdb.BuildConfig
import com.personal.tmdb.detail.data.models.MediaDetailDto
import com.personal.tmdb.core.data.models.MediaDto
import com.personal.tmdb.detail.data.models.CollectionDto
import com.personal.tmdb.search.data.models.SearchDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/trending/all/{time_window}?")
    suspend fun getTrending(
        @Path("time_window") timeWindow: String,
        @Query("language") language: String?
    ): MediaDto

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/{media_type}/{media_id}?")
    suspend fun getMovieTvDetail(
        @Path("media_type") mediaType: String,
        @Path("media_id") mediaId: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?
    ): MediaDetailDto

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/search/{search_type}?")
    suspend fun searchFor(
        @Path("search_type") searchType: String,
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean?,
        @Query("language") language: String?,
        @Query("page") page: Int
    ): SearchDto

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/collection/{collectionId}?")
    suspend fun getCollection(
        @Path("collectionId") collectionId: Int,
        @Query("language") language: String?
    ): CollectionDto
}