package com.personal.tmdb.detail.presentation.person.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.personal.tmdb.R
import com.personal.tmdb.detail.presentation.person.PersonCreditsState
import com.personal.tmdb.detail.presentation.person.PersonUiEvent

@Composable
fun PersonCreditsSorting(
    modifier: Modifier = Modifier,
    personCreditsState: () -> PersonCreditsState,
    personUiEvent: (PersonUiEvent) -> Unit
) {
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.credits),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
        IconButton(
            onClick = { showBottomSheet = !showBottomSheet }
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_page_info_fill0_wght400), contentDescription = null)
        }
    }
    if (showBottomSheet) {
        PersonSortModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            personCreditsState = personCreditsState,
            personUiEvent = personUiEvent
        )
    }
}