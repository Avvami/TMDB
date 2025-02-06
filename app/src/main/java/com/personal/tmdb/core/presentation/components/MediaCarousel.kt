package com.personal.tmdb.core.presentation.components

import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.OverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaCarousel(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    titleContent: @Composable () -> Unit = {},
    items: LazyListScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProvideTextStyle(
            value = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
        ) {
            Box(modifier = Modifier.padding(contentPadding)) {
                titleContent()
            }
        }
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides if (Build.VERSION.SDK_INT > 30) OverscrollConfiguration() else null
        ) {
            LazyRow(
                contentPadding = contentPadding,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items()
            }
        }
    }
}