package com.personal.tmdb.core.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.personal.tmdb.core.domain.models.DropdownItem

@Composable
fun CustomDropdownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    dropDownItems: List<DropdownItem?>
) {
    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        shape = MaterialTheme.shapes.small,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 0.dp
    ) {
        dropDownItems.fastForEach { dropdownItem ->
            dropdownItem?.let { item ->
                DropdownMenuItem(
                    leadingIcon = {
                        item.iconRes?.let { iconRes ->
                            Icon(
                                painter = painterResource(id = iconRes),
                                contentDescription = "Dropdown item icon"
                            )
                        }
                    },
                    text = {
                        Text(
                            text = item.text.asString(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    onClick = { item.onItemClick() },
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.onSurface,
                        leadingIconColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                )
            }
        }
    }
}