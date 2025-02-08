package com.personal.tmdb.core.domain.models

import androidx.annotation.DrawableRes
import com.personal.tmdb.core.domain.util.UiText

data class DropdownItem(
    @DrawableRes val iconRes: Int? = null,
    val text: UiText,
    val onItemClick: () -> Unit
)