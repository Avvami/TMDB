package com.personal.tmdb.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconChip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    icon: @Composable () -> Unit,
    shape: Shape = IconChipDefaults.shape,
    colors: IconChipColors = IconChipDefaults.iconChipColors(),
    border: BorderStroke? = IconChipDefaults.iconChipBorder(enabled),
    interactionSource: MutableInteractionSource? = null,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
        shape = shape,
        color = colors.containerColor(enabled),
        border = border,
        interactionSource = interactionSource
    ) {
        CompositionLocalProvider(
            LocalContentColor provides colors.iconContentColor(enabled)
        ) {
            Box(
                modifier = Modifier
                    .defaultMinSize(minHeight = IconChipDefaults.Height)
                    .padding(IconChipPadding),
                contentAlignment = Alignment.Center,
                content = { icon() }
            )
        }
    }
}

/** Contains the baseline values used by [IconChip]. */
object IconChipDefaults {
    /**
     * The height applied for a icon chip. Note that you can override it by applying
     * Modifier.height directly on a chip.
     */
    val Height = 32.0.dp

    /** The size of a chip icon. */
    val IconSize = 18.0.dp

    /**
     * Creates a [IconChipColors] that represents the default container and icon colors used in
     * a flat [IconChip].
     */
    @Composable fun iconChipColors() = IconChipColors(
        containerColor = Color.Transparent,
        iconContentColor = MaterialTheme.colorScheme.primary,
        disabledContainerColor = Color.Transparent,
        disabledIconContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = DisabledIconOpacity)
    )

    /**
     * Creates a [IconChipColors] that represents the default container and icon colors used in
     * a flat [IconChip].
     *
     * @param containerColor the container color of this chip when enabled
     * @param iconContentColor the color of this chip's icon when enabled
     * @param disabledContainerColor the container color of this chip when not enabled
     * @param disabledIconContentColor the color of this chip's icon when not enabled
     */
    @Composable
    fun iconChipColors(
        containerColor: Color = Color.Unspecified,
        iconContentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledIconContentColor: Color = Color.Unspecified
    ): IconChipColors = iconChipColors().copy(
        containerColor = containerColor,
        iconContentColor = iconContentColor,
        disabledContainerColor = disabledContainerColor,
        disabledIconContentColor = disabledIconContentColor,
    )

    /**
     * Creates a [BorderStroke] that represents the default border used in a flat [IconChip].
     *
     * @param enabled whether the chip is enabled
     * @param borderColor the border color of this chip when enabled
     * @param disabledBorderColor the border color of this chip when not enabled
     * @param borderWidth the border stroke width of this chip
     */
    @Composable
    fun iconChipBorder(
        enabled: Boolean,
        borderColor: Color = MaterialTheme.colorScheme.outline,
        disabledBorderColor: Color =
            MaterialTheme.colorScheme.onSurface.copy(
                alpha = DisabledOutlineOpacity
            ),
        borderWidth: Dp = OutlineWidth,
    ): BorderStroke = BorderStroke(borderWidth, if (enabled) borderColor else disabledBorderColor)

    /** Default shape of an icon chip. */
    val shape: Shape
        @Composable get() = MaterialTheme.shapes.small
}

@Immutable
class IconChipColors(
    val containerColor: Color,
    val iconContentColor: Color,
    val disabledContainerColor: Color,
    val disabledIconContentColor: Color
) {
    /**
     * Returns a copy of this IconChipColors, optionally overriding some of the values. This uses the
     * Color.Unspecified to mean “use the value from the source”
     */
    fun copy(
        containerColor: Color = this.containerColor,
        iconContentColor: Color = this.iconContentColor,
        disabledContainerColor: Color = this.disabledContainerColor,
        disabledIconContentColor: Color = this.disabledIconContentColor,
    ) = IconChipColors(
        containerColor.takeOrElse { this.containerColor },
        iconContentColor.takeOrElse { this.iconContentColor },
        disabledContainerColor.takeOrElse { this.disabledContainerColor },
        disabledIconContentColor.takeOrElse { this.disabledIconContentColor },
    )

    /**
     * Represents the container color for this chip, depending on [enabled].
     *
     * @param enabled whether the chip is enabled
     */
    @Stable
    internal fun containerColor(enabled: Boolean): Color =
        if (enabled) containerColor else disabledContainerColor

    /**
     * Represents the icon's content color for this chip, depending on [enabled].
     *
     * @param enabled whether the chip is enabled
     */
    @Stable
    internal fun iconContentColor(enabled: Boolean): Color =
        if (enabled) iconContentColor else disabledIconContentColor
}

/** The padding between the elements in the chip. */
private val HorizontalElementsPadding = 7.dp

/** Returns the [PaddingValues] for the icon chip. */
private val IconChipPadding = PaddingValues(horizontal = HorizontalElementsPadding)

private const val DisabledIconOpacity = 0.38f
private const val DisabledOutlineOpacity = 0.12f
private val OutlineWidth = 1.0.dp