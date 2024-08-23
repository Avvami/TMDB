package com.personal.tmdb.detail.presentation.cast.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.navigation.RootNavGraph
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.shimmerEffect

@Composable
fun CastInfoCard(
    onNavigateTo: (route: String) -> Unit,
    profileId: Int,
    profilePath: String?,
    name: String,
    character: String? = null,
    department: String? = null,
    activity: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onNavigateTo(RootNavGraph.PERSON + "/${name}/${profileId}")
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape),
            model = C.TMDB_IMAGES_BASE_URL + C.PROFILE_W185 + profilePath,
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            contentDescription = "Profile",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium
            )
            character?.let { character ->
                Text(
                    text = "as $character",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            department?.let { department ->
                Text(
                    text = department,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (!activity.isNullOrEmpty()) {
                Text(
                    text = activity,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun CastInfoCardShimmer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .shimmerEffect(),
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
                text = "Person's name",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Transparent
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
                text = "",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}