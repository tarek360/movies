package com.tarek360.movies.domain

import com.tarek360.movies.model.Movie
import com.tarek360.movies.model.YearOfMovies
import io.reactivex.Observable

interface MoviesListInteractor {
    fun getMoviesList(): Observable<List<Movie>>
    fun searchMovies(searchKey: String): Observable<List<YearOfMovies>>
    fun clear()
}
