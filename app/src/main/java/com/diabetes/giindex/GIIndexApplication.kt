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
        
        applicationScope.launch {
            DatabaseInitializer.initializeIfNeeded(this@GIIndexApplication, database)
        }
    }
}
