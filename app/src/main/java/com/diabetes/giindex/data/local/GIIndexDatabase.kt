package com.diabetes.giindex.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.diabetes.giindex.data.local.dao.*
import com.diabetes.giindex.data.local.entity.*

@Database(
    entities = [
        Product::class,
        DataSource::class,
        ProductSource::class,
        ProductSourceData::class,
        TranslationCache::class,
        SyncLog::class
    ],
    version = 2,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class GIIndexDatabase : RoomDatabase() {
    
    abstract fun productDao(): ProductDao
    abstract fun dataSourceDao(): DataSourceDao
    abstract fun productSourceDao(): ProductSourceDao
    abstract fun productSourceDataDao(): ProductSourceDataDao
    abstract fun translationCacheDao(): TranslationCacheDao
    abstract fun syncLogDao(): SyncLogDao
    
    companion object {
        @Volatile
        private var INSTANCE: GIIndexDatabase? = null
        
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `product_source_data` (
                        `productId` INTEGER NOT NULL,
                        `sourceId` INTEGER NOT NULL,
                        `gi` INTEGER NOT NULL,
                        `gl` REAL,
                        `carbsPer100g` REAL,
                        `portionSize` INTEGER,
                        `portionUnit` TEXT,
                        `notes` TEXT,
                        `addedAt` INTEGER NOT NULL,
                        PRIMARY KEY(`productId`, `sourceId`),
                        FOREIGN KEY(`productId`) REFERENCES `products`(`id`) ON DELETE CASCADE,
                        FOREIGN KEY(`sourceId`) REFERENCES `data_sources`(`id`) ON DELETE CASCADE
                    )
                """.trimIndent())
                
                database.execSQL("CREATE INDEX IF NOT EXISTS `index_product_source_data_productId` ON `product_source_data` (`productId`)")
                database.execSQL("CREATE INDEX IF NOT EXISTS `index_product_source_data_sourceId` ON `product_source_data` (`sourceId`)")
            }
        }
        
        fun getDatabase(context: Context): GIIndexDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GIIndexDatabase::class.java,
                    "giindex_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
