package com.personal.tmdb.auth.data.repository

import com.personal.tmdb.auth.data.mappers.toUserInfo
import com.personal.tmdb.auth.data.models.AccessTokenBody
import com.personal.tmdb.auth.data.models.AccessTokenDto
import com.personal.tmdb.auth.data.models.RedirectToBody
import com.personal.tmdb.auth.data.models.RequestTokenBody
import com.personal.tmdb.auth.data.models.RequestTokenDto
import com.personal.tmdb.auth.data.models.SessionDto
import com.personal.tmdb.auth.domain.models.UserInfo
import com.personal.tmdb.auth.domain.repository.AuthRepository
import com.personal.tmdb.core.data.remote.TmdbApi
import com.personal.tmdb.core.domain.util.Resource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val tmdbApi: TmdbApi
): AuthRepository {
    override suspend fun createRequestToken(redirectToBody: RedirectToBody): Resource<RequestTokenDto> {
        return try {
            Resource.Success(
                data = tmdbApi.createRequestToken(redirectTo = redirectToBody)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }

    override suspend fun createAccessToken(requestTokenBody: RequestTokenBody): Resource<AccessTokenDto> {
        return try {
            Resource.Success(
                data = tmdbApi.createAccessToken(requestToken = requestTokenBody)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }

    override suspend fun createSession(accessTokenBody: AccessTokenBody): Resource<SessionDto> {
        return try {
            Resource.Success(
                data = tmdbApi.createSession(accessToken = accessTokenBody)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }

    override suspend fun getUserDetails(sessionId: String): Resource<UserInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getUserDetails(sessionId).toUserInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }
}