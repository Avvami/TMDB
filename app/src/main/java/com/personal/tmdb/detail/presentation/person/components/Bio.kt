package com.personal.tmdb.detail.presentation.person.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.detail.domain.models.PersonInfo
import com.personal.tmdb.detail.presentation.person.PersonUiEvent

@Composable
fun Bio(
    modifier: Modifier = Modifier,
    personInfo: () -> PersonInfo,
    isBioCollapsed: () -> Boolean,
    personUiEvent: (PersonUiEvent) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.bio),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
        if (personInfo().biography.isNullOrEmpty()) {
            Text(
                text = stringResource(id = R.string.no_bio, personInfo().name),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.extraSmall)
                    .animateContentSize()
                    .clickable {
                        personUiEvent(PersonUiEvent.ChangeCollapsedBioState)
                    },
                text = personInfo().biography ?: "",
                style = MaterialTheme.typography.bodyLarge,
                maxLines = if (isBioCollapsed()) 6 else Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}