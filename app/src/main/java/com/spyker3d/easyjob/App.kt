package com.spyker3d.easyjob

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.spyker3d.easyjob.di.dataModule
import com.spyker3d.easyjob.di.interactorModule
import com.spyker3d.easyjob.di.repositoryModule
import com.spyker3d.easyjob.di.viewModelModule

class App : Application() {
    companion object {
        const val USER_AGENT = "User-Agent: FutureJob/1.0 (79950321710@yandex.ru)"
        const val LOCALE = "RU"
        const val HOST = "hh.ru"
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule, interactorModule, repositoryModule, viewModelModule)
        }
    }
}
