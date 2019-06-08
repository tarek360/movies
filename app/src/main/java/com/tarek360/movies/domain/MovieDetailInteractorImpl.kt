package com.tarek360.movies.domain

import com.tarek360.movies.model.Movie
import io.reactivex.Observable

interface MovieDetailInteractor {
    fun getMovie(index: Int): Observable<Movie>
}