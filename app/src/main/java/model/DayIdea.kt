package com.rama.app30days.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DayIdea(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @StringRes val reference: Int,
    @DrawableRes val image: Int
)