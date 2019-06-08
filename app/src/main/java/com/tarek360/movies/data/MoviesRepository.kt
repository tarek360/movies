package com.tarek360.movies.data

import com.tarek360.movies.model.Movie
import io.reactivex.Observable

interface MoviesRepository {
    fun getMoviesObservable(): Observable<List<Movie>>
    fun clear()
}
