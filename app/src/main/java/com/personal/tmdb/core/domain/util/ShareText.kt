package com.personal.tmdb.core.domain.util

import android.content.Context
import android.content.Intent

fun Context.shareText(text: String) {
    val sendIntent = Intent (
        Intent.ACTION_SEND
    ).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}