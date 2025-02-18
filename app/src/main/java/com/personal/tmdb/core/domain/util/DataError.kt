package com.personal.tmdb.core.domain.util

sealed interface DataError: Error {
    sealed class Remote: DataError {
        data object RequestTimeout: Remote()
        data object TooManyRequests: Remote()
        data object NoInternet: Remote()
        data object InvalidService: Remote()
        data object InternalError: Remote()
        data object InvalidHeader: Remote()
        data object ApiMaintenance: Remote()
        data object BackedConnection: Remote()
        data object BackendTimeout: Remote()
        data object Server: Remote()
        data object Serialization: Remote()
        data object Unknown: Remote()
        data class Custom(val statusMessage: String): Remote()
    }

    sealed class Local: DataError {
        data object DiskFull: Local()
        data object Unknown: Local()
    }
}