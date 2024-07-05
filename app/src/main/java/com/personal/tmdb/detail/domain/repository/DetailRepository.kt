package com.personal.tmdb.detail.domain.repository

import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.domain.models.CollectionInfo
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import com.personal.tmdb.detail.domain.models.SeasonInfo

interface DetailRepository {

    suspend fun getMediaDetail(mediaType: String, mediaId: Int, language: String? = null, appendToResponse: String? = null): Resource<MediaDetailInfo>

    suspend fun getCollection(collectionId: Int, language: String? = null): Resource<CollectionInfo>

    suspend fun getSeasonDetail(seriesId: Int, seasonNumber: Int, language: String? = null): Resource<SeasonInfo>
}