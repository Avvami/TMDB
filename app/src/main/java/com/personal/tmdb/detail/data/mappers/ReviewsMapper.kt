package com.personal.tmdb.detail.data.mappers

import com.personal.tmdb.detail.data.models.Review
import com.personal.tmdb.detail.data.models.Reviews
import com.personal.tmdb.detail.domain.models.ReviewInfo
import com.personal.tmdb.detail.domain.models.ReviewsResponseInfo
import java.time.LocalDate
import java.time.OffsetDateTime

fun Reviews.toReviewsResponseInfo(): ReviewsResponseInfo {
    return ReviewsResponseInfo(
        page = page,
        results = results.map { it.toReviewInfo() },
        totalPages = totalPages,
        totalResults = totalResults
    )
}

fun Review.toReviewInfo(): ReviewInfo {
    val createdAt: LocalDate? = try {
        createdAt?.takeIf { it.isNotBlank() }?.let { string ->
            OffsetDateTime.parse(string).toLocalDate()
        }
    } catch (e: Exception) {
        null
    }
    return ReviewInfo(
        author = author,
        authorDetails = authorDetails,
        content = if (content.isNullOrEmpty()) null else content,
        createdAt = createdAt,
        id = id,
        url = url
    )
}