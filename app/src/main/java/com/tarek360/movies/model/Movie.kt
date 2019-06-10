package com.tarek360.movies.model

data class MoviesData(val movies: List<Movie>)

data class Movie(
    val id: Int,
    val title: String,
    val year: Int,
    val cast: List<String>,
    val genres: List<String>,
    val rating: Int
)

data class YearOfMovies(val year: Int, val topFiveMovies: List<Movie>)


data class SearchResult(val years: List<YearOfMovies>) {

    val size: Int by lazy {
        var count = 0
        years.forEach {
            count++
            count += it.topFiveMovies.size
        }
        count
    }



}
