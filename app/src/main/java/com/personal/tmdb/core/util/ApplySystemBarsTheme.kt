package com.personal.tmdb.core.util

import android.content.Context
import android.view.View
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun ApplySystemBarsTheme(applyLightStatusBars: Boolean) {
    val view = LocalView.current
    val context = LocalContext.current

    if (!view.isInEditMode) {
        SideEffect {
            (context as? ComponentActivity)?.let { activity ->
                WindowCompat.getInsetsController(activity.window, view).apply {
                    isAppearanceLightStatusBars = !applyLightStatusBars
                    isAppearanceLightNavigationBars = !applyLightStatusBars
                }
            }
        }
    }
}

@Composable
fun ApplyStatusBarsTheme(applyLightStatusBars: Boolean) {
    val view = LocalView.current
    val context = LocalContext.current

    if (!view.isInEditMode) {
        SideEffect {
            (context as? ComponentActivity)?.let { activity ->
                WindowCompat.getInsetsController(activity.window, view).apply {
                    isAppearanceLightStatusBars = !applyLightStatusBars
                }
            }
        }
    }
}

fun applyStatusBarsTheme(view: View, context: Context, applyLightStatusBars: Boolean) {
    if (!view.isInEditMode) {
        (context as? ComponentActivity)?.let { activity ->
            WindowCompat.getInsetsController(activity.window, view).apply {
                isAppearanceLightStatusBars = !applyLightStatusBars
            }
        }
    }
}