package com.personal.tmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.personal.tmdb.ui.theme.ExtendedTheme
import com.personal.tmdb.ui.theme.TMDBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TMDBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = ExtendedTheme.colorScheme.surfaceContainerLowest
                ) {
                    Greeting("Hello New project")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Box(modifier = Modifier.size(50.dp).background(ExtendedTheme.colorScheme.surfaceDim))
        Box(modifier = Modifier.size(50.dp).background(ExtendedTheme.colorScheme.surfaceContainerLow))
        Box(modifier = Modifier.size(50.dp).background(ExtendedTheme.colorScheme.surfaceContainerHigh))
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TMDBTheme {
        Greeting("Android")
    }
}