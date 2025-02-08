package com.personal.tmdb.detail.presentation.person.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.domain.util.formatDate
import com.personal.tmdb.core.domain.util.formatGender
import com.personal.tmdb.detail.domain.models.PersonInfo
import java.time.LocalDate

@Composable
fun PersonInfo(
    modifier: Modifier = Modifier,
    personInfo: () -> PersonInfo
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.personal_info),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
        personInfo().knownForDepartment?.let { knownFor ->
            Column {
                Text(
                    text = stringResource(id = R.string.known_for),
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = knownFor,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
        Column {
            Text(
                text = stringResource(id = R.string.gender),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = formatGender(personInfo().gender).asString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
        }
        personInfo().birthday?.let { birthday ->
            Column {
                Text(
                    text = stringResource(id = R.string.birthday),
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = if (personInfo().deathday == null)
                        "${formatDate(birthday)} (${LocalDate.now().year - birthday.year} years old)"
                    else
                        formatDate(birthday),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
            personInfo().deathday?.let { deathday ->
                Column {
                    Text(
                        text = stringResource(id = R.string.deathday),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "${formatDate(deathday)} (${deathday.year - birthday.year} years old)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }
        personInfo().placeOfBirth?.let { placeOfBirth ->
            Column {
                Text(
                    text = stringResource(id = R.string.place_of_birth),
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = placeOfBirth,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}