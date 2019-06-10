package com.tarek360.movies.domain

import com.tarek360.movies.data.MoviesRepository
import com.tarek360.movies.model.Movie
import com.tarek360.movies.model.YearOfMovies
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MoviesListInteractorImpl(private val moviesRepository: MoviesRepository) : MoviesListInteractor {

    override fun getMoviesList(): Observable<List<Movie>> {
        return moviesRepository.getMoviesObservable()
            .observeOn(Schedulers.io())
    }

    override fun searchMovies(searchKey: String): Observable<List<YearOfMovies>> {
        return getMoviesList().map {
            yearsOfMovies(searchKey, it)
        }
    }

    private fun yearsOfMovies(searchKey: String, source: List<Movie>): List<YearOfMovies> {
        val ratedYearOfMoviesMap = hashMapOf<Int, RatedYearOfMovies>()

        source.filter { movie ->
            with(searchKey.trim()) {
                movie.title.contains(this, ignoreCase = true)
            }
        }.forEach { movie ->

            val ratedYearOfMovies: RatedYearOfMovies = ratedYearOfMoviesMap[movie.year] ?: RatedYearOfMovies(movie.year)
            ratedYearOfMovies.addMovie(movie)

            ratedYearOfMoviesMap[movie.year] = ratedYearOfMovies
        }

        return ratedYearOfMoviesMap.values.map {
            YearOfMovies(year = it.year, topFiveMovies = getTopFiveMovies(it))
        }.sortedBy { it.year }
    }

    override fun clear() = moviesRepository.clear()

    private fun getTopFiveMovies(ratedYearOfMovies: RatedYearOfMovies): List<Movie> {
        val list = ArrayList<Movie>()
        for (rate in RATING_LEVELS downTo 1) {
            ratedYearOfMovies.getMoviesByRating(rate).forEach { movie ->
                list.add(movie)
                if (list.size >= MAX_MOVIES_COUNT) {
                    return list
                }
            }
        }
        return list
    }
}


private const val MAX_MOVIES_COUNT = 5
private const val RATING_LEVELS = 5

private data class RatedYearOfMovies(val year: Int) {

    private val movies = Array<ArrayList<Movie>>(RATING_LEVELS) { ArrayList() }

    fun addMovie(movie: Movie) {
        movies[movie.rating - 1].add(movie)
    }

    fun getMoviesByRating(rating: Int): List<Movie> {
        return movies[rating - 1]
    }

}
