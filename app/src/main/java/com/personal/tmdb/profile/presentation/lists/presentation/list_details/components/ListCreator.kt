package com.personal.tmdb.profile.presentation.lists.presentation.list_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.data.models.CreatedBy
import com.personal.tmdb.core.domain.util.C
import com.personal.tmdb.core.domain.util.shimmerEffect

@Composable
fun ListCreator(
    modifier: Modifier = Modifier,
    createdBy: CreatedBy
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            model = if (createdBy.avatarPath == null) {
                C.GRAVATAR_IMAGES_BASE_URL.format(createdBy.gravatarHash)
            } else {
                C.TMDB_IMAGES_BASE_URL + C.PROFILE_W185 + createdBy.avatarPath
            },
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            contentScale = ContentScale.Crop
        )
        val annotatedString = buildAnnotatedString {
            withStyle(style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface).toSpanStyle()) {
                append("A list by ")
            }
            withStyle(style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.surfaceVariant).toSpanStyle()) {
                append(createdBy.username ?: "unknown")
            }
        }
        Text(
            modifier = Modifier.weight(1f),
            text = annotatedString
        )
    }
}

@Composable
fun ListCreatorShimmer(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .shimmerEffect()
        )
        Text(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .shimmerEffect(),
            text = "A list by elon_mAss"
        )
    }
}