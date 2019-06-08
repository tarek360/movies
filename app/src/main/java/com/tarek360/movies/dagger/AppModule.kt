package com.tarek360.movies.dagger

import android.app.Application
import android.content.Context
import com.tarek360.movies.data.MoviesRepository
import com.tarek360.movies.data.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideMoviesRepository(context: Context): MoviesRepository = MoviesRepositoryImpl(context)
}
