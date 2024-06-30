package com.personal.tmdb.core.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DropdownItem(
    @DrawableRes val iconRes: Int? = null,
    @StringRes val textRes: Int? = null,
    val text: String? = null,
    val selected: Boolean? = null,
    val onItemClick: () -> Unit
)