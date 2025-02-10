package com.personal.tmdb.core.domain.util

sealed interface DataError: Error {
    enum class Remote: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        INVALID_SERVICE,
        INTERNAL_ERROR,
        INVALID_HEADER,
        API_MAINTENANCE,
        BACKEND_CONNECTION,
        BACKEND_TIMEOUT,
        SERVER,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local: DataError {
        DISK_FULL,
        UNKNOWN
    }
}