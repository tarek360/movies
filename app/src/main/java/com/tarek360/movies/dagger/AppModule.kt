package com.tarek360.movies.dagger

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.tarek360.movies.data.MoviesRepository
import com.tarek360.movies.data.MoviesRepositoryImpl
import com.tarek360.movies.rx.SchedulerProvider
import com.tarek360.movies.rx.SchedulerProviderImpl
import com.tarek360.movies.viewmodel.MovieViewModelProviders
import com.tarek360.movies.viewmodel.MovieViewModelProvidersImpl
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

    @Provides
    fun provideMovieViewModelProviders(factory: ViewModelProvider.Factory): MovieViewModelProviders {
        return MovieViewModelProvidersImpl(factory)
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return SchedulerProviderImpl()
    }
}
