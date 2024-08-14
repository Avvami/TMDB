package com.personal.tmdb.detail.presentation.person.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import coil.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.navigation.RootNavGraph
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.formatPersonActing
import com.personal.tmdb.detail.domain.models.CombinedCastCrewInfo

@Composable
fun PersonKnownForMedia(
    modifier: Modifier = Modifier,
    onNavigateTo: (route: String) -> Unit,
    info: () -> CombinedCastCrewInfo,
    preferencesState: State<PreferencesState>
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .clickable {
                when (info().mediaType) {
                    MediaType.TV, MediaType.MOVIE -> {
                        onNavigateTo(RootNavGraph.DETAIL + "/${info().mediaType.name.lowercase()}/${info().id}")
                    }

                    MediaType.PERSON -> {
                        onNavigateTo(RootNavGraph.PERSON + "/${info().name ?: ""}/${info().id}")
                    }

                    else -> {
                        /*TODO: Navigate to lost your way screen*/
                    }
                }
            }
            .padding(horizontal = 2.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.widthIn(35.dp),
                text = info().releaseDate?.year?.toString() ?: stringResource(id = R.string.dash),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Icon(
                modifier = Modifier.size(12.dp),
                painter = painterResource(id = R.drawable.icon_fiber_manual_record_fill0_wght400),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            AsyncImage(
                modifier = Modifier
                    .height(80.dp)
                    .aspectRatio(0.675f)
                    .clip(RoundedCornerShape((preferencesState.value.corners / 2.25).dp)),
                model = C.TMDB_IMAGES_BASE_URL + C.POSTER_W154 + info().posterPath,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder),
                contentDescription = "Poster",
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            info().name?.let { name ->
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Column {
                info().jobs?.fastForEach { jobs ->
                    formatPersonActing(jobs.episodeCount, jobs.character, jobs.job).let { annotatedString ->
                        if (annotatedString.isNotEmpty()) {
                            Text(
                                text = annotatedString,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}