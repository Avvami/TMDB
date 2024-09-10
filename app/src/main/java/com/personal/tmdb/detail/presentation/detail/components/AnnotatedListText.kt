package com.personal.tmdb.detail.presentation.detail.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import com.personal.tmdb.core.navigation.RootNavGraph

data class AnnotatedItem(
    val id: Int,
    val name: String
)

@Composable
fun AnnotatedListText(
    modifier: Modifier = Modifier,
    annotationTag: AnnotationTag,
    titlePrefix: String? = null,
    items: List<AnnotatedItem>,
    titlePrefixStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontStyle = MaterialTheme.typography.labelLarge.fontStyle,
        fontSize = MaterialTheme.typography.labelLarge.fontSize,
        fontWeight = MaterialTheme.typography.labelLarge.fontWeight
    ),
    itemsStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
        fontSize = MaterialTheme.typography.bodyMedium.fontSize
    ),
    onNavigateTo: (route: String) -> Unit
) {
    val annotatedString = buildAnnotatedString {
        titlePrefix?.let {
            withStyle(style = titlePrefixStyle) {
                append("$titlePrefix ")
            }
        }
        items.forEachIndexed { index, item ->
            withLink(
                LinkAnnotation.Clickable(
                    tag = annotationTag.name,
                    styles = TextLinkStyles(style = itemsStyle),
                    linkInteractionListener = {
                        when(annotationTag) {
                            AnnotationTag.GENRE -> {
                                /*TODO: Navigate to genre screen*/
                            }
                            AnnotationTag.CAST -> {
                                onNavigateTo(RootNavGraph.PERSON + "/${item.name}/${item.id}")
                            }
                        }
                    }
                )
            ) {
                append(item.name)
            }
            if (index < items.size - 1) {
                withStyle(style = itemsStyle) {
                    append(", ")
                }
            }
        }
    }
    Text(
        modifier = modifier,
        text = annotatedString
    )
}

enum class AnnotationTag {
    GENRE, CAST
}