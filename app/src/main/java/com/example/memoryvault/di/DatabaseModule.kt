package com.example.memoryvault.di

import android.content.Context
import androidx.room.Room
import com.example.memoryvault.data.local.dao.MemoryDao
import com.example.memoryvault.data.local.database.AppDatabase
import com.example.memoryvault.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideMemoryDao(appDatabase: AppDatabase): MemoryDao{
        return appDatabase.memoryDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

}