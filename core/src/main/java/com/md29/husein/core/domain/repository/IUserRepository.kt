package com.md29.husein.core.domain.repository

import com.md29.husein.core.data.Resource
import com.md29.husein.core.domain.model.Follow
import com.md29.husein.core.domain.model.User
import com.md29.husein.core.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getAllUser(name: String): Flow<Resource<List<User>>>

    fun getFavoriteUser(): Flow<List<User>>

    fun setFavoriteUser(user: User, state: Boolean)

    suspend fun getUserDetail(name: String): Flow<Resource<UserDetail>>

    suspend fun getFollow(name: String): Flow<Resource<List<Follow>>>

    suspend fun getFollowing(name: String): Flow<Resource<List<Follow>>>

}