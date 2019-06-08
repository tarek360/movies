package com.tarek360.movies.domain

import com.tarek360.movies.data.MoviesRepository
import com.tarek360.movies.model.Movie
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MovieDetailInteractorImpl(private val moviesRepository: MoviesRepository) : MovieDetailInteractor {

    override fun getMovie(index: Int): Observable<Movie> {
        return moviesRepository.getMoviesObservable()
            .observeOn(Schedulers.io())
            .map { it[index] }
    }
}
