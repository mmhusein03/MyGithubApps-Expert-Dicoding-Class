package com.md29.husein.core.data.source.local

import com.md29.husein.core.data.source.local.entity.UserEntity
import com.md29.husein.core.data.source.local.room.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val userDao: UserDao) {

    fun getAllUser(q: String): Flow<List<UserEntity>> = userDao.getAllUser(q)

    fun getFavoriteUser(): Flow<List<UserEntity>> = userDao.getFavoriteUser()

    suspend fun insertUser(userList: List<UserEntity>) = userDao.insertUser(userList)

    fun setFavoriteUser(user: UserEntity, newState: Boolean) {
        user.isFavorite = newState
        userDao.updateFavoriteUser(user)
    }
}