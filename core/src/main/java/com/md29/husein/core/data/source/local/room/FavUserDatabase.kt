package com.md29.husein.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.md29.husein.core.data.source.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class FavUserDatabase : RoomDatabase() {
    abstract fun userFavDao(): UserDao
}