package com.tarek360.movies.ui.moviedetail

sealed class MovieDetailIntent {
    data class LoadMovieIntent(val movieId: Int) : MovieDetailIntent()
}
