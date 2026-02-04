package com.diabetes.giindex

import android.app.Application
import com.diabetes.giindex.data.DatabaseInitializer
import com.diabetes.giindex.data.local.GIIndexDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class GIIndexApplication : Application() {
    
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    val database: GIIndexDatabase by lazy { 
        GIIndexDatabase.getDatabase(this) 
    }
    
    override fun onCreate() {
        super.onCreate()
        
        android.util.Log.d("GIIndexApplication", "Application onCreate called")
        
        applicationScope.launch {
            try {
                android.util.Log.d("GIIndexApplication", "Starting database initialization")
                DatabaseInitializer.initializeDatabase(database)
                android.util.Log.d("GIIndexApplication", "Database initialization completed")
            } catch (e: Exception) {
                android.util.Log.e("GIIndexApplication", "Error initializing database", e)
            }
        }
    }
}
