package com.personal.tmdb.core.data.remote

import com.personal.tmdb.core.domain.util.DataError
import com.personal.tmdb.core.domain.util.Result
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> T
): Result<T, DataError.Remote> {
    return try {
        val result = apiCall()
        Result.Success(result)
    } catch (e: SocketTimeoutException) {
        Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnknownHostException) {
        Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: HttpException) {
        when (e.code()) {
            406 -> Result.Error(DataError.Remote.INVALID_HEADER)
            408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
            429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
            500 -> Result.Error(DataError.Remote.INTERNAL_ERROR)
            501 -> Result.Error(DataError.Remote.INVALID_SERVICE)
            502 -> Result.Error(DataError.Remote.BACKEND_CONNECTION)
            503 -> Result.Error(DataError.Remote.API_MAINTENANCE)
            504 -> Result.Error(DataError.Remote.BACKEND_TIMEOUT)
            in 505..599 -> Result.Error(DataError.Remote.SERVER)
            else -> Result.Error(DataError.Remote.UNKNOWN)
        }
    } catch (e: Exception) {
        Result.Error(DataError.Remote.UNKNOWN)
    }
}