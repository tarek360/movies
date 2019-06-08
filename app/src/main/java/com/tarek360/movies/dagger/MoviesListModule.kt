package com.tarek360.movies.dagger

import com.tarek360.movies.data.MoviesRepository
import com.tarek360.movies.domain.MoviesListInteractor
import com.tarek360.movies.domain.MoviesListInteractorImpl
import dagger.Module
import dagger.Provides

@Module
class MoviesListModule {

    @Provides
    fun provideMoviesListInteractor(moviesRepository: MoviesRepository): MoviesListInteractor =
        MoviesListInteractorImpl(moviesRepository)
}
