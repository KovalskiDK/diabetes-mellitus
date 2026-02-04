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
        SyncLog::class,
        AIAnalysisCache::class
    ],
    version = 4,
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
    abstract fun aiAnalysisCacheDao(): AIAnalysisCacheDao
    
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
        
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Добавляем колонку sourceId в таблицу products
                database.execSQL("ALTER TABLE products ADD COLUMN sourceId INTEGER NOT NULL DEFAULT 0")
                
                // Создаем индекс для sourceId
                database.execSQL("CREATE INDEX IF NOT EXISTS `index_products_sourceId` ON `products` (`sourceId`)")
            }
        }
        
        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Создаем таблицу для кэширования AI-анализов
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `ai_analysis_cache` (
                        `productId` INTEGER NOT NULL,
                        `recommendation` TEXT NOT NULL,
                        `explanation` TEXT NOT NULL,
                        `tips` TEXT NOT NULL,
                        `isAiGenerated` INTEGER NOT NULL,
                        `timestamp` INTEGER NOT NULL,
                        `productGi` INTEGER NOT NULL,
                        `productCarbs` REAL NOT NULL,
                        PRIMARY KEY(`productId`)
                    )
                """.trimIndent())
            }
        }
        
        fun getDatabase(context: Context): GIIndexDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GIIndexDatabase::class.java,
                    "giindex_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
