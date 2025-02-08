package com.personal.tmdb.core.domain.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ColorMatrixColorFilter

fun duotoneColorFilter(
    color1: Color, color2: Color
): ColorFilter {

    val c1Red = color1.red
    val c1Green = color1.green
    val c1Blue = color1.blue

    val c2Red = color2.red
    val c2Green = color2.green
    val c2Blue = color2.blue

    val duoToneArray = floatArrayOf(
        c1Red - c2Red, 0f, 0f, 0f, c2Red,
        c1Green - c2Green, 0f, 0f, 0f, c2Green,
        c1Blue - c2Blue, 0f, 0f, 0f, c2Blue,
        0f, 0f, 0f, 1f, 0f
    )

    return ColorMatrixColorFilter(ColorMatrix(duoToneArray))
}
