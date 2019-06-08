package com.tarek360.movies.dagger

import com.tarek360.movies.data.MoviesRepository
import com.tarek360.movies.domain.MovieDetailInteractor
import com.tarek360.movies.domain.MovieDetailInteractorImpl
import dagger.Module
import dagger.Provides

@Module
class MovieDetailModule {

    @Provides
    fun provideMoviesListInteractor(moviesRepository: MoviesRepository): MovieDetailInteractor = MovieDetailInteractorImpl(moviesRepository)
}
