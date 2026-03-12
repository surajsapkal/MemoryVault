package com.example.memoryvault.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.memoryvault.data.local.dao.MemoryDao
import com.example.memoryvault.utils.Constants.DATABASE_NAME

@Database(entities = [Entity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun memoryDao(): MemoryDao

    companion object{

        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance
                instance
            }
        }

    }

}