package com.personal.tmdb.detail.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.util.C
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import com.personal.tmdb.detail.presentation.detail.DetailUiEvent
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.onBackgroundLight

@Composable
fun Trailer(
    info: () -> MediaDetailInfo,
    detailUiEvent: (DetailUiEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.tmdb),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = C.TMDB_IMAGES_BASE_URL + C.BACKDROP_W1280 + info().backdropPath,
            contentDescription = "Backdrop",
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(onBackgroundLight.copy(.3f))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            if (false) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "Play",
                    tint = backgroundLight
                )
            }
        }
    }
}