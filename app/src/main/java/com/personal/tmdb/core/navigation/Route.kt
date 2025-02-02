package com.personal.tmdb.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Home: Route

    @Serializable
    data object Search: Route

    @Serializable
    data class Profile(val approved: Boolean?): Route

    @Serializable
    data class Detail(val mediaType: String, val mediaId: Int): Route

    @Serializable
    data class Reviews(val mediaType: String, val mediaId: Int, val selectedReviewIndex: Int? = null): Route

    @Serializable
    data class Episodes(val mediaId: Int, val seasonNumber: Int): Route

    @Serializable
    data class Episode(val mediaId: Int, val seasonNumber: Int, val episodeNumber: Int): Route

    @Serializable
    data class Collection(val collectionId: Int): Route

    @Serializable
    data class Cast(val mediaName: String, val mediaType: String, val mediaId: Int, val seasonNumber: Int? = null, val episodeNumber: Int? = null): Route

    @Serializable
    data class Person(val personName: String, val personId: Int): Route

    @Serializable
    data class Image(val imageType: String, val imagesPath: String, val selectedImageIndex: Int? = null): Route

    @Serializable
    data object Settings: Route

    @Serializable
    data object Appearance: Route
}