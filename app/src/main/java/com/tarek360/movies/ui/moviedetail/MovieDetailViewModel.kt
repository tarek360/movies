package com.tarek360.movies.ui.moviedetail

import com.tarek360.movies.viewmodel.BaseViewModel
import com.tarek360.movies.domain.MovieDetailInteractor
import com.tarek360.movies.rx.SchedulerProvider
import timber.log.Timber
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val movieDetailInteractor: MovieDetailInteractor
) :
    BaseViewModel<MovieDetailIntent, MovieDetailState>() {

    override fun handleIntent(intent: MovieDetailIntent) {
        when (intent) {
            is MovieDetailIntent.LoadMovieIntent -> {
                loadMovieIntent(intent)
            }
        }
    }

    private fun loadMovieIntent(intent: MovieDetailIntent.LoadMovieIntent) {
        Timber.d("Intent: load movie intent!")

        if (currentViewState == MovieDetailState.LoadingState || currentViewState is MovieDetailState.DataState) {
            return
        }

        compositeDisposable.add(movieDetailInteractor.getMovie(intent.movieId)
            .map<MovieDetailState> { MovieDetailState.DataState(it) }
            .onErrorReturn { MovieDetailState.ErrorState("Error") }
            .startWith(MovieDetailState.LoadingState)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.main())
            .subscribe {
                currentViewState = it
            })
    }
}
