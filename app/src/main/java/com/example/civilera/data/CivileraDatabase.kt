package com.example.civilera.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Site::class, Material::class, Transaction::class], version = 1, exportSchema = false)
abstract class CivileraDatabase : RoomDatabase() {
    abstract fun siteDao(): SiteDao
    abstract fun materialDao(): MaterialDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: CivileraDatabase? = null

        fun getDatabase(context: Context): CivileraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CivileraDatabase::class.java,
                    "civilera_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
