package com.tarek360.movies.mapper

import com.tarek360.movies.formatter.CastTextFormatter
import com.tarek360.movies.formatter.GenresTextFormatter
import com.tarek360.movies.model.Movie
import com.tarek360.movies.ui.moviedetail.MovieDetailData

class MovieDetailDataMapperImpl(
    private val castTextFormatter: CastTextFormatter,
    private val genresTextFormatter: GenresTextFormatter
) : MovieDetailDataMapper {

    override fun map(movie: Movie): MovieDetailData {
        return MovieDetailData(
            title = movie.title,
            year = movie.year.toString(),
            cast = castTextFormatter.format(movie.cast),
            genres = genresTextFormatter.format(movie.genres)
        )
    }
}
