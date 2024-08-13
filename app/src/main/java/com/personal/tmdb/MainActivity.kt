package com.personal.tmdb

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.personal.tmdb.core.navigation.RootNavigationGraph
import com.personal.tmdb.ui.theme.TMDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            mainViewModel.holdSplash
        }
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            TMDBTheme(
                darkTheme = mainViewModel.preferencesState.collectAsStateWithLifecycle().value.darkTheme ?: isSystemInDarkTheme()
            ) {
                Surface {
                    RootNavigationGraph(
                        navController = rememberNavController(),
                        mainViewModel = mainViewModel
                    )
                }
            }
        }
    }

    fun openCustomChromeTab(url: String) {
        val chromeIntent = CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(CustomTabColorSchemeParams.Builder()
                .setToolbarColor(ContextCompat.getColor(this, R.color.backgroundLight))
                .build())
            .setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, CustomTabColorSchemeParams.Builder()
                .setToolbarColor(ContextCompat.getColor(this, R.color.backgroundDark))
                .build())
            .setUrlBarHidingEnabled(true)
            .setShowTitle(true)
            .build()
        chromeIntent.launchUrl(this, Uri.parse(url))
    }
}