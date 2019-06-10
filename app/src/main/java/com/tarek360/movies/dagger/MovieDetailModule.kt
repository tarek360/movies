package com.tarek360.movies.dagger

import com.tarek360.movies.data.MoviesRepository
import com.tarek360.movies.domain.MovieDetailInteractor
import com.tarek360.movies.domain.MovieDetailInteractorImpl
import com.tarek360.movies.formatter.CastTextFormatter
import com.tarek360.movies.formatter.CastTextFormatterImpl
import com.tarek360.movies.formatter.GenresTextFormatter
import com.tarek360.movies.formatter.GenresTextFormatterImpl
import com.tarek360.movies.mapper.MovieDetailDataMapper
import com.tarek360.movies.mapper.MovieDetailDataMapperImpl
import dagger.Module
import dagger.Provides

@Module
class MovieDetailModule {

    @Provides
    fun provideMoviesListInteractor(moviesRepository: MoviesRepository): MovieDetailInteractor =
        MovieDetailInteractorImpl(moviesRepository)

    @Provides
    fun provideCastTextFormatter(): CastTextFormatter = CastTextFormatterImpl()

    @Provides
    fun provideGenresTextFormatter(): GenresTextFormatter = GenresTextFormatterImpl()

    @Provides
    fun provideMovieDetailDataMapper(
        castTextFormatter: CastTextFormatter,
        genresTextFormatter: GenresTextFormatter
    ): MovieDetailDataMapper {
        return MovieDetailDataMapperImpl(castTextFormatter, genresTextFormatter)
    }
}
