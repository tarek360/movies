package com.tarek360.movies.ui.moviedetail

import com.tarek360.movies.viewmodel.BaseViewModel
import com.tarek360.movies.domain.MovieDetailInteractor
import com.tarek360.movies.formatter.CastTextFormatter
import com.tarek360.movies.formatter.GenresTextFormatter
import com.tarek360.movies.mapper.FlickrPhotoMapper
import com.tarek360.movies.mapper.MovieDetailDataMapper
import com.tarek360.movies.rx.SchedulerProvider
import timber.log.Timber
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val movieDetailInteractor: MovieDetailInteractor,
    private val movieDetailDataMapper: MovieDetailDataMapper,
    private val flickrPhotoMapper: FlickrPhotoMapper
) :
    BaseViewModel<MovieDetailIntent, MovieDetailState, MovieDetailAction>() {

    override fun handleIntent(intent: MovieDetailIntent) {
        when (intent) {
            is MovieDetailIntent.LoadMovieIntent -> {
                loadMovieIntent(intent)
            }
        }
    }

    private fun loadMovieIntent(intent: MovieDetailIntent.LoadMovieIntent) {
        Timber.d("Intent: load movie intent!")

        if (currentViewState == MovieDetailState.LoadingState.MovieLoadingState
            || currentViewState is MovieDetailState.DataState
        ) {
            return
        }

        compositeDisposable.add(movieDetailInteractor.getMovie(intent.movieId)
            .map<MovieDetailState> { MovieDetailState.DataState(movieDetailDataMapper.map(it)) }
            .onErrorReturn { MovieDetailState.ErrorState("Error") }
            .startWith(MovieDetailState.LoadingState.MovieLoadingState)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.main())
            .subscribe {
                currentViewState = it
                if (it is MovieDetailState.DataState) {
                    loadPhotos(it.data.title)
                }
            })
    }

    private fun loadPhotos(movieTitle: String) {
        val viewState = currentViewState
        currentViewState = MovieDetailState.LoadingState.PhotosLoadingState
        currentViewState = viewState

        if (viewState is MovieDetailState.DataState) {
            compositeDisposable.add(movieDetailInteractor.searchPhotos(movieTitle)
                .map<MovieDetailState> {
                    Thread.sleep(4000)
                    val photos = flickrPhotoMapper.map(it)
                    MovieDetailState.DataState(viewState.data.copy(photos = photos))
                }
                .onErrorReturn { MovieDetailState.ErrorState("Error") }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .subscribe {
                    currentViewState = it
                }
            )
        }
    }

}
