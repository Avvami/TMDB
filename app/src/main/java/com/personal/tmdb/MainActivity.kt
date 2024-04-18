package com.personal.tmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import com.personal.tmdb.home.presentation.home.HomeScreen
import com.personal.tmdb.ui.theme.TMDBTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            TMDBTheme(
                darkTheme = false
            ) {
                Surface {
                    HomeScreen()
                }
            }
        }
    }
}