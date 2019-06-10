package com.tarek360.movies.domain

import com.tarek360.movies.model.Movie
import com.tarek360.movies.network.FlickrPhoto
import io.reactivex.Observable

interface MovieDetailInteractor {
    fun getMovie(movieId: Int): Observable<Movie>
    fun searchPhotos(movieTitle: String): Observable<List<FlickrPhoto>>
}
