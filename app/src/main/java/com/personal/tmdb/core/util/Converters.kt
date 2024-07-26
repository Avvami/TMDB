package com.personal.tmdb.core.util

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