package com.md29.husein.core.di

import android.content.Context
import androidx.room.Room
import com.md29.husein.core.data.source.local.room.FavUserDatabase
import com.md29.husein.core.data.source.local.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FavUserDatabase =
        Room.databaseBuilder(
            context,
            FavUserDatabase::class.java, "UserEntity.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideUserDao(database: FavUserDatabase): UserDao = database.userFavDao()
}