package dev.jmyp.movies.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(context: Context, appDeclaration: KoinAppDeclaration = {}) = startKoin {
    androidContext(context)
    appDeclaration()
    modules(appModules)
}