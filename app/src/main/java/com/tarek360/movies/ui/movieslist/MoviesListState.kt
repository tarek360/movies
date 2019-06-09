package com.tarek360.movies.ui.movieslist

import com.tarek360.movies.model.Movie

sealed class MoviesListState {
    object LoadingState : MoviesListState()
    data class DataState(val data: List<Movie>) : MoviesListState()
    data class ErrorState(val data: String) : MoviesListState()
}
