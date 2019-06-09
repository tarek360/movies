package com.tarek360.movies.ui.moviedetail

import com.tarek360.movies.model.Movie

sealed class MovieDetailState {
    object LoadingState : MovieDetailState()
    data class DataState(val data: Movie) : MovieDetailState()
    data class ErrorState(val data: String) : MovieDetailState()
}
