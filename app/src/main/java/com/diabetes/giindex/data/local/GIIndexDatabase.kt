package com.diabetes.giindex.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.diabetes.giindex.data.local.dao.*
import com.diabetes.giindex.data.local.entity.*

@Database(
    entities = [
        Product::class,
        DataSource::class,
        ProductSource::class,
        TranslationCache::class,
        SyncLog::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class GIIndexDatabase : RoomDatabase() {
    
    abstract fun productDao(): ProductDao
    abstract fun dataSourceDao(): DataSourceDao
    abstract fun productSourceDao(): ProductSourceDao
    abstract fun translationCacheDao(): TranslationCacheDao
    abstract fun syncLogDao(): SyncLogDao
    
    companion object {
        @Volatile
        private var INSTANCE: GIIndexDatabase? = null
        
        fun getDatabase(context: Context): GIIndexDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GIIndexDatabase::class.java,
                    "gi_index_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
