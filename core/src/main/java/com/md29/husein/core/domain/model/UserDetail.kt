package com.md29.husein.core.domain.model

data class UserDetail(
    val id: Int,
    val nameUser: String? = null,
    val name: String,
    val followers: Int? = null,
    val following: Int? = null,
    val avatar: String,
)
