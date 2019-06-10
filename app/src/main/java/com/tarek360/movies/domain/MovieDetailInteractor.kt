package com.tarek360.movies.domain

import com.tarek360.movies.data.MoviesRepository
import com.tarek360.movies.model.Movie
import com.tarek360.movies.network.FlickrApi
import com.tarek360.movies.network.FlickrPhoto
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MovieDetailInteractorImpl(
    private val moviesRepository: MoviesRepository,
    private val flickrApi: FlickrApi
) : MovieDetailInteractor {

    override fun getMovie(movieId: Int): Observable<Movie> {
        return moviesRepository.getMoviesObservable()
            .observeOn(Schedulers.io())
            .map { it[movieId] }
    }

    override fun searchPhotos(movieTitle: String): Observable<List<FlickrPhoto>> {
        return flickrApi.searchPhotos(movieTitle = movieTitle)
            .map {
                it.flickrPhotos.photos
            }
    }
}
