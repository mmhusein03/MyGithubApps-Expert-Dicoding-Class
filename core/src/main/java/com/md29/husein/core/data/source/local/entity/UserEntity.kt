package com.md29.husein.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "login")
    val name: String,

    @ColumnInfo(name = "avatar")
    val avatar: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,
) : Parcelable