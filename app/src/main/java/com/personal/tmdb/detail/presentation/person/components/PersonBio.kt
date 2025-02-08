package com.personal.tmdb.detail.presentation.person.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.tmdb.R
import com.personal.tmdb.detail.domain.models.PersonInfo

@Composable
fun PersonBio(
    modifier: Modifier = Modifier,
    personInfo: () -> PersonInfo
) {
    var collapsed by rememberSaveable { mutableStateOf(true) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.bio),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
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
                    .animateContentSize()
                    .clickable(
                        interactionSource = null,
                        indication = null
                    ) {
                        collapsed = !collapsed
                    },
                text = personInfo().biography ?: "",
                style = MaterialTheme.typography.bodyLarge,
                maxLines = if (collapsed) 6 else Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}