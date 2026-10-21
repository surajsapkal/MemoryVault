package com.example.memoryvault.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.impl.Migration_1_2
import com.example.memoryvault.data.local.dao.MemoryDao
import com.example.memoryvault.data.local.entity.MemoryEntity
import com.example.memoryvault.utils.Constants.DATABASE_NAME
import com.example.memoryvault.utils.Constants.TABLE_NAME

@Database(
    entities = [MemoryEntity::class],
    version = 2,
    exportSchema = true,
)
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
                ).addMigrations(MIGRATION_1_2).build()

                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add the new column with a default value
                database.execSQL(
                    "ALTER $TABLE_NAME ADD COLUMN age INTEGER NOT NULL DEFAULT 0"
                )
            }
        }

    }*/

}