package com.personal.tmdb.auth.domain.repository

import com.personal.tmdb.auth.data.models.AccessTokenBody
import com.personal.tmdb.auth.data.models.AccessTokenDto
import com.personal.tmdb.auth.data.models.RedirectToBody
import com.personal.tmdb.auth.data.models.RequestTokenBody
import com.personal.tmdb.auth.data.models.RequestTokenDto
import com.personal.tmdb.auth.data.models.SessionDto
import com.personal.tmdb.auth.domain.models.UserInfo
import com.personal.tmdb.core.domain.util.Resource

interface AuthRepository {

    suspend fun createRequestToken(redirectToBody: RedirectToBody): Resource<RequestTokenDto>

    suspend fun createAccessToken(requestTokenBody: RequestTokenBody): Resource<AccessTokenDto>

    suspend fun createSession(accessTokenBody: AccessTokenBody): Resource<SessionDto>

    suspend fun getUserDetails(sessionId: String): Resource<UserInfo>
}