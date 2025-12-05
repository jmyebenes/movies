package dev.jmyp.movies

import android.app.Application
import dev.jmyp.movies.di.initKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin(this)
    }
}