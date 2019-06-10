package com.tarek360.movies.ui.movieslist


sealed class MoviesListState {
    object LoadingState : MoviesListState()
//    data class DataState(val data: List<RecyclerViewItem>) : MoviesListState()

    sealed class DataState(open val data: List<RecyclerViewItem>) : MoviesListState() {
        data class AllMoviesState(override val data: List<RecyclerViewItem>) : DataState(data)
        data class SearchResultState(override val data: List<RecyclerViewItem>) : DataState(data)
    }

    data class ErrorState(val data: String) : MoviesListState()
}
