package com.personal.tmdb.core.util

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import com.personal.tmdb.R
import com.personal.tmdb.ui.theme.tmdbRatingGreen
import com.personal.tmdb.ui.theme.tmdbRatingOrange
import com.personal.tmdb.ui.theme.tmdbRatingRed
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.NavigableMap
import java.util.TreeMap


fun formatRuntime(minutes: Int, context: Context): String {
    return when {
        minutes < 60 -> UiText.StringResource(R.string.runtime_min, minutes).asString(context)
        minutes == 60 -> UiText.StringResource(R.string.runtime_h, minutes).asString(context)
        else -> {
            val hours = minutes / 60
            val remainingMinutes = minutes % 60
            UiText.StringResource(R.string.runtime_full, hours, remainingMinutes).asString(context)
        }
    }
}

@Composable
fun formatVoteAverageToColor(vote: Float): Color {
    return when (vote) {
        in 0f ..< 3.5f -> tmdbRatingRed
        in 3.5f ..< 7f -> tmdbRatingOrange
        in 7f .. 10f -> tmdbRatingGreen
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
}

fun formatVoteAverage(voteAverage: Float): String {
    val roundedValue = (Math.round(voteAverage * 10) / 10.0).toFloat()
    return when {
        roundedValue == 0f -> "NR"
        roundedValue % 1.0 == 0.0 -> roundedValue.toInt().toString()
        else -> "%.1f".format(voteAverage)
    }
}

fun formatTvShowRuntime(numberOfSeasons: Int, numberOfEpisodes: Int): String {
    return when (numberOfSeasons) {
        1 -> formatEpisodesCount(numberOfEpisodes)
        else -> "$numberOfSeasons Seasons"
    }
}

fun formatEpisodesCount(numberOfEpisodes: Int): String {
    return when (numberOfEpisodes) {
        1 -> "$numberOfEpisodes Episode"
        else -> "$numberOfEpisodes Episodes"
    }
}

fun formatDate(localDate: LocalDate): String = localDate.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))

fun formatGender(genderCode: Int, context: Context): String {
    return when (genderCode) {
        1 -> UiText.StringResource(R.string.female).asString(context)
        2 -> UiText.StringResource(R.string.male).asString(context)
        3 -> UiText.StringResource(R.string.non_binary).asString(context)
        else -> UiText.StringResource(R.string.gender_not_specified).asString(context)
    }
}

fun formatPersonActing(numberOfEpisodes: Int?, character: String?, job: String?): AnnotatedString {
    return buildAnnotatedString {
        numberOfEpisodes?.let {
            append("(${formatEpisodesCount(it)})")
        }
        if (numberOfEpisodes != null && (!character.isNullOrEmpty() || !job.isNullOrEmpty())) {
            append(" ")
        }
        if (!character.isNullOrEmpty()) {
            append("as $character")
        }
        if (!job.isNullOrEmpty()) {
            append("... $job")
        }
    }
}

private val suffixes: NavigableMap<Long, String> = TreeMap<Long, String>().apply {
    put(1_000L, "K")
    put(1_000_000L, "M")
    put(1_000_000_000L, "B")
    put(1_000_000_000_000L, "T")
    put(1_000_000_000_000_000L, "P")
    put(1_000_000_000_000_000_000L, "E")
}

fun compactDecimalFormat(value: Long): String {
    if (value < 0) return "-" + compactDecimalFormat(-value)
    if (value < 1000) return value.toString()

    val (divideBy, suffix) = suffixes.floorEntry(value)?.toPair() ?: return value.toString()

    val truncated = value / (divideBy / 10)
    val hasDecimal = truncated < 100 && (truncated / 10.0) != (truncated / 10).toDouble()
    return if (hasDecimal) (truncated / 10.0).toString() + suffix else (truncated / 10).toString() + suffix
}