package com.md29.husein.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int = 0,
    val name: String,
    val avatar: String,
    val isFavorite: Boolean
) : Parcelable
