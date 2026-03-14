package com.example.memoryvault.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.memoryvault.data.local.dao.MemoryDao
import com.example.memoryvault.data.local.entity.MemoryEntity
import com.example.memoryvault.utils.Constants.DATABASE_NAME

@Database(entities = [MemoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun memoryDao(): MemoryDao

    // handled in Hilt
    /*companion object{

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

    }*/

}