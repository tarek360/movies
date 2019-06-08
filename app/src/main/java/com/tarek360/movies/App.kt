package com.tarek360.movies

import android.app.Application
import com.tarek360.movies.dagger.AppComponent
import com.tarek360.movies.dagger.AppModule
import com.tarek360.movies.dagger.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }

    private fun initDagger(app: App): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
}
