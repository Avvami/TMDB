package com.personal.tmdb.detail.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExternalIds(
    @Json(name = "facebook_id")
    val facebookId: String?,
    @Json(name = "freebase_id")
    val freebaseId: String?,
    @Json(name = "freebase_mid")
    val freebaseMid: String?,
    @Json(name = "imdb_id")
    val imdbId: String?,
    @Json(name = "instagram_id")
    val instagramId: String?,
    @Json(name = "tiktok_id")
    val tiktokId: String?,
    @Json(name = "tvrage_id")
    val tvrageId: String?,
    @Json(name = "twitter_id")
    val twitterId: String?,
    @Json(name = "wikidata_id")
    val wikidataId: String?,
    @Json(name = "youtube_id")
    val youtubeId: String?
)