package com.aktasbdr.cryptotracker

import android.app.Application
import com.aktasbdr.cryptotracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
class CryptoTrackerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // androidContext'i eger bir dependencyimiz contexte ihtiyaci olursa diye kullaniyoruz
            androidContext(this@CryptoTrackerApp)
            androidLogger()
            modules(appModule)
        }
    }
}