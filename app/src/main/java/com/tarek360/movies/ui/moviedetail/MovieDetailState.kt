package com.tarek360.movies.ui.moviedetail

sealed class MovieDetailState {
    object LoadingState : MovieDetailState()
    data class DataState(val data: MovieDetailData) : MovieDetailState()
    data class ErrorState(val data: String) : MovieDetailState()
}

sealed class MovieDetailAction

data class MovieDetailData(
    val title: String,
    val year: String,
    val cast: String,
    val genres: String
)
