package com.personal.tmdb.core.domain.util

import com.personal.tmdb.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun convertStringToDate(dateString: String?): LocalDate? {
    return try {
        dateString?.let { string ->
            LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE)
        }
    } catch (e: Exception) {
        null
    }
}

fun DataError.toUiText(): UiText {
    val stringRes = when (this) {
        DataError.Local.DISK_FULL -> R.string.error_disk_full
        DataError.Local.UNKNOWN -> R.string.error_unknown
        DataError.Remote.REQUEST_TIMEOUT -> R.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> R.string.error_request_timeout
        DataError.Remote.NO_INTERNET -> R.string.error_no_internet
        DataError.Remote.INVALID_SERVICE -> R.string.error_invalid_service
        DataError.Remote.INTERNAL_ERROR -> R.string.error_internal_error
        DataError.Remote.INVALID_HEADER -> R.string.error_invalid_header
        DataError.Remote.API_MAINTENANCE -> R.string.error_api_maintenance
        DataError.Remote.BACKEND_CONNECTION -> R.string.error_backend_connection
        DataError.Remote.BACKEND_TIMEOUT -> R.string.error_backend_timeout
        DataError.Remote.SERVER -> R.string.error_unknown
        DataError.Remote.SERIALIZATION -> R.string.error_serialization
        DataError.Remote.UNKNOWN -> R.string.error_unknown
    }

    return UiText.StringResource(stringRes)
}