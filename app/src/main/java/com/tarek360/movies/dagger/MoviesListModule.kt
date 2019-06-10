package com.tarek360.movies.dagger

import com.tarek360.movies.data.MoviesRepository
import com.tarek360.movies.domain.MoviesListInteractor
import com.tarek360.movies.domain.MoviesListInteractorImpl
import com.tarek360.movies.mapper.RecyclerViewItemMapper
import com.tarek360.movies.mapper.RecyclerViewItemMapperImpl
import dagger.Module
import dagger.Provides

@Module
class MoviesListModule {

    @Provides
    fun provideMoviesListInteractor(moviesRepository: MoviesRepository): MoviesListInteractor =
        MoviesListInteractorImpl(moviesRepository)

    @Provides
    fun provideRecyclerViewItemMapper(): RecyclerViewItemMapper = RecyclerViewItemMapperImpl()
}
