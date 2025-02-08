package com.personal.tmdb.detail.presentation.cast.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.util.fastForEachIndexed
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.util.formatEpisodesCount

data class AnnotatedCastItem(
    val id: String,
    val role: String,
    val episodes: Int
)

@Composable
fun annotatedCastText(
    items: List<AnnotatedCastItem>,
    linkStyle: SpanStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.surfaceVariant).toSpanStyle(),
    textStyle: SpanStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface).toSpanStyle(),
    onNavigateTo: (route: Route) -> Unit
): AnnotatedString {
    val annotatedString by remember {
        derivedStateOf {
            buildAnnotatedString {
                items.fastForEachIndexed { index, item ->
                    withStyle(style = textStyle) {
                        append(item.role)
                    }
                    withLink(
                        LinkAnnotation.Clickable(
                            tag = "role",
                            styles = TextLinkStyles(style = linkStyle),
                            linkInteractionListener = {
                                /*TODO: Navigate*/
                                println(item.role + item.id)
                            }
                        )
                    ) {
                        append(" (${formatEpisodesCount(item.episodes)})")
                    }
                    if (index != items.lastIndex) {
                        withStyle(style = textStyle) {
                            append(", ")
                        }
                    }
                }
            }
        }
    }
    return annotatedString
}