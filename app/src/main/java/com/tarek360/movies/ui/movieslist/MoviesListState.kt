package com.tarek360.movies.ui.movieslist


sealed class MoviesListState {
    object LoadingState : MoviesListState()

    sealed class DataState(open val data: List<RecyclerViewItem>) : MoviesListState() {
        data class AllMoviesState(override val data: List<RecyclerViewItem>) : DataState(data)
        data class SearchResultState(override val data: List<RecyclerViewItem>) : DataState(data)
    }

    data class ErrorState(val data: String) : MoviesListState()
}


sealed class MoviesListAction {
    data class OpenMovieAction(val movieId: Int) : MoviesListAction()
}
