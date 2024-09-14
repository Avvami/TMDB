package com.personal.tmdb.core.data.remote

import com.personal.tmdb.BuildConfig
import com.personal.tmdb.auth.data.models.AccessTokenBody
import com.personal.tmdb.auth.data.models.AccessTokenDto
import com.personal.tmdb.auth.data.models.RedirectToBody
import com.personal.tmdb.auth.data.models.RequestTokenBody
import com.personal.tmdb.auth.data.models.RequestTokenDto
import com.personal.tmdb.auth.data.models.SessionDto
import com.personal.tmdb.auth.data.models.UserDto
import com.personal.tmdb.core.data.models.MediaResponseDto
import com.personal.tmdb.detail.data.models.CollectionDto
import com.personal.tmdb.detail.data.models.Credits
import com.personal.tmdb.detail.data.models.EpisodeDetailsDto
import com.personal.tmdb.detail.data.models.Images
import com.personal.tmdb.detail.data.models.MediaDetailDto
import com.personal.tmdb.detail.data.models.PersonDto
import com.personal.tmdb.detail.data.models.Reviews
import com.personal.tmdb.detail.data.models.SeasonDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/trending/all/{time_window}?")
    suspend fun getTrendingList(
        @Path("time_window") timeWindow: String,
        @Query("language") language: String?
    ): MediaResponseDto

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/{media_type}/{media_id}?")
    suspend fun getMovieTvDetail(
        @Path("media_type") mediaType: String,
        @Path("media_id") mediaId: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?,
        @Query("include_image_language") includeImageLanguage: String?
    ): MediaDetailDto

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/search/{search_type}?")
    suspend fun searchFor(
        @Path("search_type") searchType: String,
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean?,
        @Query("language") language: String?,
        @Query("page") page: Int
    ): MediaResponseDto

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/collection/{collectionId}?")
    suspend fun getCollection(
        @Path("collectionId") collectionId: Int,
        @Query("language") language: String?
    ): CollectionDto

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/{media_type}/popular?")
    suspend fun getPopularList(
        @Path("media_type") mediaType: String,
        @Query("language") language: String?,
        @Query("page") page: Int
    ): MediaResponseDto

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/tv/{series_id}/season/{season_number}?")
    suspend fun getSeasonDetails(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("language") language: String?
    ): SeasonDto

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/{media_type}/{media_id}/{method}?")
    suspend fun getCredits(
        @Path("media_type") mediaType: String,
        @Path("media_id") seriesId: Int,
        @Path("method") method: String,
        @Query("language") language: String?
    ): Credits

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/tv/{series_id}/season/{season_number}/episode/{episode_number}/credits?")
    suspend fun getEpisodeCredits(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int,
        @Query("language") language: String?
    ): Credits

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/person/{person_id}?")
    suspend fun getPerson(
        @Path("person_id") personId: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?
    ): PersonDto

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @POST("4/auth/request_token")
    suspend fun createRequestToken(
        @Body redirectTo: RedirectToBody
    ): RequestTokenDto

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @POST("4/auth/access_token")
    suspend fun createAccessToken(
        @Body requestToken: RequestTokenBody
    ): AccessTokenDto

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @POST("3/authentication/session/convert/4")
    suspend fun createSession(
        @Body accessToken: AccessTokenBody
    ): SessionDto

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/account?")
    suspend fun getUserDetails(
        @Query("session_id") sessionId: String
    ): UserDto

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/tv/{series_id}/season/{season_number}/episode/{episode_number}?")
    suspend fun getEpisodeDetails(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?,
        @Query("include_image_language") includeImageLanguage: String?
    ): EpisodeDetailsDto

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("3/{path}?")
    suspend fun getImages(
        @Path("path") path: String,
        @Query("language") language: String?,
        @Query("include_image_language") includeImageLanguage: String?
    ): Images

    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("/3/{media_type}/{media_id}/reviews?")
    suspend fun getReviews(
        @Path("media_type") mediaType: String,
        @Path("media_id") mediaId: Int,
        @Query("page") page: Int,
        @Query("language") language: String?
    ): Reviews
}