package com.md29.husein.core.utils

import com.md29.husein.core.data.source.local.entity.UserEntity
import com.md29.husein.core.data.source.remote.response.DetailGitUser
import com.md29.husein.core.data.source.remote.response.ItemsItem
import com.md29.husein.core.domain.model.Follow
import com.md29.husein.core.domain.model.User
import com.md29.husein.core.domain.model.UserDetail

object DataMapper {
    fun mapResponsesToEntities(input: List<ItemsItem>): List<UserEntity> {
        val userEntityList = ArrayList<UserEntity>()
        input.map {
            val userEntity = UserEntity(
                id = it.id,
                name = it.login,
                avatar = it.avatarUrl
            )
            userEntityList.add(userEntity)
        }
        return userEntityList
    }

    fun mapResponsesFollowToDomain(input: List<ItemsItem>): List<Follow> {
        val userList = ArrayList<Follow>()
        input.map {
            val user = Follow(
                id = it.id,
                name = it.login,
                avatar = it.avatarUrl,
            )
            userList.add(user)
        }
        return userList
    }

    fun mapEntitiesToDomain(input: List<UserEntity>): List<User> =
        input.map {
            User(
                id = it.id,
                name = it.name,
                avatar = it.avatar,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntities(it: User): UserEntity =
            UserEntity(
                id = it.id,
                name = it.name,
                avatar = it.avatar,
                isFavorite = it.isFavorite
            )

    fun mapResponseDetailToDomain(it: DetailGitUser): UserDetail =
        UserDetail(
            id = it.id,
            name = it.name,
            nameUser = it.nameUser,
            avatar = it.avatar,
            following = it.following,
            followers = it.followers
        )

}