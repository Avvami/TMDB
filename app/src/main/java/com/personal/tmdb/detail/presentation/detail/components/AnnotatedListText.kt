package com.personal.tmdb.detail.presentation.detail.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
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
            val start = length
            addStringAnnotation(
                tag = annotationTag.name,
                annotation = item.id.toString(),
                start = start,
                end = start + item.name.length
            )
            withStyle(style = itemsStyle) {
                append(item.name)
            }
            if (index < items.size - 1) {
                withStyle(style = itemsStyle) {
                    append(", ")
                }
            }
        }
    }
    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = annotationTag.name, start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    val itemId = annotation.item.toIntOrNull()
                    val item = items.find { it.id == itemId }
                    if (item != null) {
                        when (annotationTag) {
                            AnnotationTag.GENRE -> {
                                /*TODO: Navigate to genre screen*/
                            }
                            AnnotationTag.CAST -> {
                                onNavigateTo(RootNavGraph.PERSON + "/${item.id}")
                            }
                        }
                    }
                }
        }
    )
}

enum class AnnotationTag {
    GENRE, CAST
}