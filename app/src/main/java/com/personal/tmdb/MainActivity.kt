package com.personal.tmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
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
                darkTheme = mainViewModel.preferencesState.collectAsStateWithLifecycle().value.isDark
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
}