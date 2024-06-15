package com.personal.tmdb.detail.domain.repository

import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.domain.models.MediaDetailInfo

interface DetailRepository {

    suspend fun getMediaDetail(mediaType: MediaType, mediaId: Int, language: String? = null, appendToResponse: String? = null): Resource<MediaDetailInfo>
}