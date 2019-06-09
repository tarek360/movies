package com.tarek360.movies.ui.movieslist

sealed class MoviesListIntent {
    object LoadMoviesListIntent : MoviesListIntent()
    data class SearchMoviesListIntent(val movieTitle: String = "") : MoviesListIntent()
}
