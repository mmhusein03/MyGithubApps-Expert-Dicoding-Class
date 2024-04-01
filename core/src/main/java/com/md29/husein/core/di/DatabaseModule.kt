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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    val passphrase: ByteArray = SQLiteDatabase.getBytes("md29".toCharArray())
    val factory = SupportFactory(passphrase)
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FavUserDatabase =
        Room.databaseBuilder(
            context,
            FavUserDatabase::class.java, "UserEntity.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()

    @Provides
    fun provideUserDao(database: FavUserDatabase): UserDao = database.userFavDao()
}