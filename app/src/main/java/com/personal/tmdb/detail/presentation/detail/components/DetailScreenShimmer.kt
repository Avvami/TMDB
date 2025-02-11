package com.personal.tmdb.detail.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.personal.tmdb.UserState

@Composable
fun DetailScreenShimmer(
    modifier: Modifier = Modifier,
    userState: () -> UserState
) {
    CompositionLocalProvider(
        LocalContentColor provides Color.Transparent
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DetailTitleShimmer()
            DetailBannerShimmer()
            DetailOverviewShimmer()
            DetailCreditsShimmer()
            DetailActionButtonsShimmer(
                userState = userState
            )
        }
    }
}