package com.personal.tmdb.detail.domain.repository

import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.domain.models.CollectionInfo
import com.personal.tmdb.detail.domain.models.CreditsInfo
import com.personal.tmdb.detail.domain.models.EpisodeDetailsInfo
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import com.personal.tmdb.detail.domain.models.PersonInfo
import com.personal.tmdb.detail.domain.models.SeasonInfo

interface DetailRepository {

    suspend fun getMediaDetail(mediaType: String, mediaId: Int, language: String? = null, appendToResponse: String? = null): Resource<MediaDetailInfo>

    suspend fun getCollection(collectionId: Int, language: String? = null): Resource<CollectionInfo>

    suspend fun getSeasonDetails(seriesId: Int, seasonNumber: Int, language: String? = null): Resource<SeasonInfo>

    suspend fun getCast(mediaType: String, mediaId: Int, method: String, language: String? = null): Resource<CreditsInfo>

    suspend fun getPerson(personId: Int, language: String? = null, appendToResponse: String?): Resource<PersonInfo>

    suspend fun getEpisodeDetails(seriesId: Int, seasonNumber: Int, episodeNumber: Int, language: String? = null, appendToResponse: String?, includeImageLanguage: String?): Resource<EpisodeDetailsInfo>
}