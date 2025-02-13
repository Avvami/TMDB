package com.personal.tmdb.core.data.repository

import com.personal.tmdb.core.data.local.UserDao
import com.personal.tmdb.core.data.mappers.toMediaResponseInfo
import com.personal.tmdb.core.data.mappers.toUser
import com.personal.tmdb.core.data.mappers.toUserEntity
import com.personal.tmdb.core.data.remote.TmdbApi
import com.personal.tmdb.core.data.remote.safeApiCall
import com.personal.tmdb.core.domain.models.MediaResponseInfo
import com.personal.tmdb.core.domain.models.User
import com.personal.tmdb.core.domain.repository.UserRepository
import com.personal.tmdb.core.domain.util.DataError
import com.personal.tmdb.core.domain.util.Result
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val tmdbApi: TmdbApi,
    private val dao: UserDao
): UserRepository {

    override suspend fun getUser(): User? = dao.getUser()?.toUser()

    override suspend fun saveUser(user: User) = dao.saveUser(user.toUserEntity())

    override suspend fun removeUser(user: User) = dao.removeUser(user.toUserEntity())

    override suspend fun getWatchlist(
        accountObjectId: String,
        mediaType: String,
        sessionId: String,
        page: Int,
        language: String?
    ): Result<MediaResponseInfo, DataError.Remote> {
        return safeApiCall {
            tmdbApi.getWatchlist(
                accountObjectId = accountObjectId,
                mediaType = mediaType,
                sessionId = sessionId,
                page = page,
                language = language
            ).toMediaResponseInfo()
        }
    }
}