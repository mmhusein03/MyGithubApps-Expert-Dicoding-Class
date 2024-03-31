package com.md29.husein.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserResponse(

    @field:SerializedName("total_count")
    var totalCount: Int,

    @field:SerializedName("items")
    val items: List<ItemsItem>
)

@Parcelize
data class ItemsItem(

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    val login: String
): Parcelable

data class DetailGitUser(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val nameUser: String,

    @field:SerializedName("login")
    val name: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("avatar_url")
    val avatar: String,
)