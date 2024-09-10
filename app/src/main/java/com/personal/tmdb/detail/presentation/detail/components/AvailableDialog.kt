package com.personal.tmdb.detail.presentation.detail.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import com.personal.tmdb.MainActivity
import com.personal.tmdb.R
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.findActivity
import com.personal.tmdb.detail.data.models.Available
import com.personal.tmdb.detail.data.models.Provider
import com.personal.tmdb.detail.presentation.detail.AvailableState
import com.personal.tmdb.detail.presentation.detail.DetailUiEvent
import com.personal.tmdb.ui.theme.justWatch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailableDialog(
    watchProviders: () -> Map<String, Available>,
    availableSearchQuery: State<String>,
    availableCountries: State<Collection<String>?>,
    availableState: () -> AvailableState,
    detailUiEvent: (DetailUiEvent) -> Unit
) {
    Dialog(
        onDismissRequest = { detailUiEvent(DetailUiEvent.ChangeAvailableDialogState) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val active = availableState().isSearchActive
            val onActiveChange = {
                detailUiEvent(DetailUiEvent.ChangeAvailableSearchState)
            }
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = availableSearchQuery.value,
                        onQueryChange = {
                            detailUiEvent(DetailUiEvent.SetAvailableSearchQuery(it))
                        },
                        onSearch = {
                            keyboardController?.hide()
                        },
                        expanded = active,
                        onExpandedChange = { onActiveChange() },
                        enabled = true,
                        placeholder = {
                            Text(
                                text = availableState().selectedCountry
                            )
                        },
                        leadingIcon = {
                            AnimatedContent(
                                targetState = availableState().isSearchActive,
                                label = "Leading search icon animation"
                            ) { targetState ->
                                if (targetState) {
                                    IconButton(
                                        onClick = { detailUiEvent(DetailUiEvent.ChangeAvailableSearchState) }
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                            contentDescription = "Close"
                                        )
                                    }
                                } else {
                                    val imageLoader = ImageLoader.Builder(LocalContext.current)
                                        .components { add(SvgDecoder.Factory()) }
                                        .build()
                                    AsyncImage(
                                        modifier = Modifier
                                            .height(16.dp)
                                            .aspectRatio(4 / 3f)
                                            .clip(MaterialTheme.shapes.extraSmall),
                                        model = "https://flagicons.lipis.dev/flags/4x3/${getCountryCode(availableState().selectedCountry)?.lowercase()}.svg",
                                        placeholder = painterResource(id = R.drawable.placeholder),
                                        error = painterResource(id = R.drawable.placeholder),
                                        contentDescription = "Flag",
                                        contentScale = ContentScale.Crop,
                                        imageLoader = imageLoader
                                    )
                                }
                            }
                        },
                        trailingIcon = {
                            IconButton(onClick = { detailUiEvent(DetailUiEvent.ChangeAvailableDialogState) }) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = "Close"
                                )
                            }
                        },
                        interactionSource = null,
                    )
                },
                expanded = active,
                onExpandedChange = { onActiveChange() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(MaterialTheme.shapes.large),
                shape = MaterialTheme.shapes.large,
                tonalElevation = SearchBarDefaults.TonalElevation,
                shadowElevation = SearchBarDefaults.ShadowElevation,
                windowInsets = SearchBarDefaults.windowInsets,
                content = {
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        availableCountries.value?.let { countries ->
                            countries.forEach { country ->
                                Text(
                                    text = country,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            detailUiEvent(DetailUiEvent.SetSelectedCountry(country))
                                        }
                                        .padding(horizontal = 16.dp, vertical = 12.dp)
                                )
                            }
                        }
                    }
                },
            )
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val activity = LocalContext.current.findActivity() as MainActivity
                watchProviders()[availableState().selectedCountry]?.let { available ->
                    val annotatedString = buildAnnotatedString {
                        val text = stringResource(id = R.string.watch_link_not_provided)
                        val tmdb = stringResource(id = R.string.tmdb)
                        val tmdbIndex = text.indexOf(tmdb, startIndex = text.indexOf(tmdb) + 4)

                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)) {
                            append(text.substring(0, tmdbIndex))
                        }
                        withLink(
                            LinkAnnotation.Clickable(
                                tag = "URL",
                                styles = TextLinkStyles(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)),
                                linkInteractionListener = {
                                    activity.openCustomChromeTab(available.link)
                                }
                            )
                        ) {
                            append(tmdb)
                        }
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)) {
                            append(text.substring(tmdbIndex + 4))
                        }
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = annotatedString,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(
                        modifier = Modifier.padding(start = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.powered_by),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Image(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.extraSmall)
                                .clickable { activity.openCustomChromeTab(C.JUSTWATCH_URL) }
                                .background(justWatch)
                                .padding(
                                    horizontal = 8.dp,
                                    vertical = 4.dp
                                ),
                            painter = painterResource(id = R.drawable.icon_just_watch),
                            contentDescription = "JustWatch"
                        )
                    }
                    available.flatrate?.let { stream ->
                        Providers(
                            categoryRes = R.string.stream,
                            providers = stream
                        )
                    }
                    available.free?.let { free ->
                        Providers(
                            categoryRes = R.string.free,
                            providers = free
                        )
                    }
                    available.buy?.let { buy ->
                        Providers(
                            categoryRes = R.string.buy,
                            providers = buy
                        )
                    }
                    available.rent?.let { rent ->
                        Providers(
                            categoryRes = R.string.rent,
                            providers = rent
                        )
                    }
                    available.ads?.let { ads ->
                        Providers(
                            categoryRes = R.string.ads,
                            providers = ads
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Providers(
    @StringRes categoryRes: Int,
    providers: List<Provider>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = categoryRes),
            style = MaterialTheme.typography.titleMedium
        )
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            providers.fastForEach { source ->
                AsyncImage(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(MaterialTheme.shapes.small),
                    model = C.TMDB_IMAGES_BASE_URL + C.LOGO_W92 + source.logoPath,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder),
                    contentDescription = source.providerName ?: "Logo",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

private fun getCountryCode(countryName: String) =
    Locale.getISOCountries().find { Locale("en", it).displayCountry == countryName }