package com.tarek360.movies.domain

import com.tarek360.movies.data.MoviesRepository
import com.tarek360.movies.model.Movie
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MoviesListInteractorImpl(private val moviesRepository: MoviesRepository) : MoviesListInteractor {

    override fun getMoviesList(): Observable<List<Movie>> {
        return moviesRepository.getMoviesObservable()
            .observeOn(Schedulers.io())
    }

    override fun clear() = moviesRepository.clear()
}
