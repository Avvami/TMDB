package com.personal.tmdb.core.util

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.personal.tmdb.R
import com.personal.tmdb.ui.theme.tmdbRatingGreen
import com.personal.tmdb.ui.theme.tmdbRatingOrange
import com.personal.tmdb.ui.theme.tmdbRatingRed
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatRuntime(minutes: Int, context: Context): String {
    return if (minutes < 60) {
        UiText.StringResource(R.string.runtime_min, minutes).asString(context)
    } else {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return UiText.StringResource(R.string.runtime_full, hours, remainingMinutes).asString(context)
    }
}

@Composable
fun formatVoteAverageToColor(vote: Float): Color {
    return when (vote) {
        in 0f .. 3.5f -> tmdbRatingRed
        in 3.6f .. 6.9f -> tmdbRatingOrange
        in 7f .. 10f -> tmdbRatingGreen
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
}

fun formatVoteAverage(voteAverage: Float): String {
    val roundedValue = "%.1f".format(voteAverage).toFloat()
    return when {
        roundedValue == 0f -> "NR"
        roundedValue % 1.0 == 0.0 -> roundedValue.toInt().toString()
        else -> roundedValue.toString()
    }
}

fun formatTvShowRuntime(numberOfSeasons: Int, numberOfEpisodes: Int): String {
    val episodes = when (numberOfEpisodes) {
        1 -> "$numberOfEpisodes Episode"
        else -> "$numberOfEpisodes Episodes"
    }
    return when (numberOfSeasons) {
        1 -> episodes
        else -> "$numberOfSeasons Seasons"
    }
}

fun formatDate(localDate: LocalDate): String = localDate.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))