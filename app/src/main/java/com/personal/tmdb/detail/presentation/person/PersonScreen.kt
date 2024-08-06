package com.personal.tmdb.detail.presentation.person

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.personal.tmdb.R
import com.personal.tmdb.detail.presentation.person.components.Bio
import com.personal.tmdb.detail.presentation.person.components.ExternalIdsRow
import com.personal.tmdb.detail.presentation.person.components.PersonKnownForMedia
import com.personal.tmdb.detail.presentation.person.components.PersonPhotoCarousel
import com.personal.tmdb.detail.presentation.person.components.PersonalInfo
import com.personal.tmdb.detail.presentation.person.components.StarringRow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PersonScreen(
    navigateBack: () -> Unit,
    onNavigateTo: (route: String) -> Unit,
    personViewModel: PersonViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    SelectionContainer {
                        Text(
                            text = personViewModel.personName,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
                    )  {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_translate_fill0_wght400),
                            contentDescription = "Translations"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            personViewModel.personState.personInfo?.let { personInfo ->
                item {
                    PersonPhotoCarousel(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        personInfo = { personInfo }
                    )
                }
                personInfo.externalIds?.let { externalIds ->
                    item {
                        ExternalIdsRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            externalIds = externalIds
                        )
                    }
                }
                item {
                    PersonalInfo(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        personInfo = { personInfo }
                    )
                }
                item {
                    Bio(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        personInfo = { personInfo },
                        isBioCollapsed = personViewModel::isBioCollapsed,
                        personUiEvent = personViewModel::personUiEvent
                    )
                }
                personInfo.combinedCreditsInfo?.let { combinedCredits ->
                    item {
                        StarringRow(
                            modifier = Modifier.padding(vertical = 8.dp),
                            onNavigateTo = onNavigateTo,
                            combinedCredits = { combinedCredits }
                        )
                    }
                }
                personViewModel.personCreditsState.personCredits?.let { personCredits ->
                    personCredits.credits?.forEach { (department, knownFor) ->
                        stickyHeader {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(horizontal = 16.dp, vertical = 4.dp),
                            ) {
                                Text(
                                    text = department ?: stringResource(id = R.string.acting),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        knownFor?.values?.forEach { infos ->
                            itemsIndexed(infos) { index, info ->
                                Column(
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                ) {
                                    PersonKnownForMedia(
                                        onNavigateTo = onNavigateTo,
                                        info = { info }
                                    )
                                    if (index == infos.lastIndex) {
                                        HorizontalDivider()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}