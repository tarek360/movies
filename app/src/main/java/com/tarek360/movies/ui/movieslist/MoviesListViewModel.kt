package com.tarek360.movies.ui.movieslist

import com.tarek360.movies.viewmodel.BaseViewModel
import com.tarek360.movies.domain.MoviesListInteractor
import com.tarek360.movies.mapper.RecyclerViewItemMapper
import com.tarek360.movies.rx.SchedulerProvider
import timber.log.Timber
import javax.inject.Inject

open class MoviesListViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val moviesListInteractor: MoviesListInteractor,
    private val recyclerViewItemMapper: RecyclerViewItemMapper
) :
    BaseViewModel<MoviesListIntent, MoviesListState, MoviesListAction>() {

    override fun handleIntent(intent: MoviesListIntent) {
        when (intent) {
            is MoviesListIntent.LoadMoviesListIntent -> {
                loadMoviesListIntent()
            }
            is MoviesListIntent.SearchMoviesListIntent -> {
                searchMoviesIntent(intent.searchKey)
            }
            is MoviesListIntent.OpenMovieIntent -> {
                setViewAction(MoviesListAction.OpenMovieAction(intent.movieId))
            }
        }
    }

    private fun loadMoviesListIntent() {
        Timber.d("Intent: load movies intent!")

        if (currentViewState == MoviesListState.LoadingState
            || currentViewState is MoviesListState.DataState.AllMoviesState
        ) {
            return
        }

        compositeDisposable.add(moviesListInteractor.getMoviesList()
            .map<MoviesListState> {
                MoviesListState.DataState.AllMoviesState(recyclerViewItemMapper.mapFromMoviesList(it))
            }
            .startWith(MoviesListState.LoadingState)
            .onErrorReturn { MoviesListState.ErrorState("Error") }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.main())
            .subscribe {
                currentViewState = it
            })
    }

    private fun searchMoviesIntent(searchKey: String) {
        Timber.d("Intent: search movies intent!")

        if (searchKey.isEmpty()) {
            loadMoviesListIntent()
            return
        }

        compositeDisposable.add(moviesListInteractor.searchMovies(searchKey)
            .map<MoviesListState> {
                MoviesListState.DataState.SearchResultState(recyclerViewItemMapper.mapFromYearsOfMoviesList(it))
            }
            .startWith(MoviesListState.LoadingState)
            .onErrorReturn { MoviesListState.ErrorState("Error") }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.main())
            .subscribe {
                Timber.d("search result $it")
                currentViewState = it
            })
    }


    override fun onCleared() {
        super.onCleared()
        moviesListInteractor.clear()
    }
}
