package com.personal.tmdb.detail.presentation.cast.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.util.fastForEachIndexed
import com.personal.tmdb.core.util.formatEpisodesCount

data class AnnotatedCastItem(
    val id: String,
    val role: String,
    val episodes: Int
)

@Composable
fun AnnotatedCastText(
    modifier: Modifier = Modifier,
    items: List<AnnotatedCastItem>,
    linkStyle: SpanStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.surfaceVariant).toSpanStyle(),
    textStyle: SpanStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface).toSpanStyle()
) {
    val annotatedString by remember {
        derivedStateOf {
            buildAnnotatedString {
                items.fastForEachIndexed { index, item ->
                    withStyle(style = textStyle) {
                        append(item.role)
                    }
                    /*TODO: Api is not good for that, maybe later...*/
//                    withLink(
//                        LinkAnnotation.Clickable(
//                            tag = "role",
//                            styles = TextLinkStyles(style = linkStyle),
//                            linkInteractionListener = {
//                                println(item.role + item.id)
//                            }
//                        )
//                    )
                    withStyle(style = linkStyle) {
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
    Text(
        modifier = modifier,
        text = annotatedString
    )
}