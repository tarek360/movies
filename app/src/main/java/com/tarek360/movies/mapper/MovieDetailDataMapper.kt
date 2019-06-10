package com.tarek360.movies.mapper

import com.tarek360.movies.model.Movie
import com.tarek360.movies.ui.moviedetail.MovieDetailData

interface MovieDetailDataMapper {
    fun map(movie: Movie): MovieDetailData
}
