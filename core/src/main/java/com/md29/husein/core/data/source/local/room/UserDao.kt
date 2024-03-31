package com.md29.husein.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.md29.husein.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: List<UserEntity>)

    @Query("SELECT * FROM user WHERE login LIKE :q")
    fun getAllUser(q: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM user WHERE isFavorite = 1")
    fun getFavoriteUser(): Flow<List<UserEntity>>

    @Update
    fun updateFavoriteUser(tourism: UserEntity)

}