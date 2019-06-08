package com.tarek360.movies.domain

import com.tarek360.movies.model.Movie
import io.reactivex.Observable

interface MoviesListInteractor {
    fun getMoviesList(): Observable<List<Movie>>
    fun clear()
}
