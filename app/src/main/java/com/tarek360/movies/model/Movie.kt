package com.tarek360.movies.model

data class MoviesData(val movies: List<Movie>)

data class Movie(
    val title: String,
    val year: Int,
    val cast: List<String>,
    val genres: List<String>,
    val rating: Int
)
