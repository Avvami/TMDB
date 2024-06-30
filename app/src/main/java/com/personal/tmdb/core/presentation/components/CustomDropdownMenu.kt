package com.personal.tmdb.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.personal.tmdb.core.domain.models.DropdownItem

@Composable
fun CustomDropdownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    dropDownItems: List<DropdownItem?>
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(8.dp))) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh)
        ) {
            dropDownItems.fastForEachIndexed { index, dropdownItem ->
                dropdownItem?.let { item ->
                    DropdownMenuItem(
                        leadingIcon = {
                            if (item.selected == true) {
                                item.iconRes?.let { iconRes ->
                                    Icon(
                                        modifier = Modifier.size(20.dp),
                                        painter = painterResource(id = iconRes),
                                        contentDescription = "Dropdown item icon"
                                    )
                                }
                            }
                        },
                        text = {
                            item.text?.let {
                                Text(text = it)
                            }
                            item.textRes?.let {
                                Text(text = stringResource(id = it))
                            }
                        },
                        onClick = { item.onItemClick() },
                        colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface, leadingIconColor = MaterialTheme.colorScheme.onSurface)
                    )
                    if (index != dropDownItems.lastIndex) {
                        HorizontalDivider(
                            modifier = if (item.iconRes != null) Modifier.padding(start = 52.dp, end = 16.dp) else Modifier,
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                    }
                }
            }
        }
    }
}