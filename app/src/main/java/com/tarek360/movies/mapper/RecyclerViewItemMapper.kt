package com.tarek360.movies.mapper

import com.tarek360.movies.model.Movie
import com.tarek360.movies.model.YearOfMovies
import com.tarek360.movies.ui.movieslist.RecyclerViewItem

interface RecyclerViewItemMapper {
    fun mapFromMoviesList(years: List<Movie>): List<RecyclerViewItem>
    fun mapFromYearsOfMoviesList(years: List<YearOfMovies>): List<RecyclerViewItem>
}
