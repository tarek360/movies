package com.tarek360.movies.mapper

import com.tarek360.movies.model.Movie
import com.tarek360.movies.model.YearOfMovies
import com.tarek360.movies.ui.movieslist.RecyclerViewItem

class RecyclerViewItemMapperImpl : RecyclerViewItemMapper {

    override fun mapFromMoviesList(years: List<Movie>): List<RecyclerViewItem> {
        return years.map {
            RecyclerViewItem.MovieItem(movie = it)
        }
    }

    override fun mapFromYearsOfMoviesList(years: List<YearOfMovies>): List<RecyclerViewItem> {

        val list = arrayListOf<RecyclerViewItem>()
        years.forEach { yearOfMovies ->
            list.add(RecyclerViewItem.YearItem(year = yearOfMovies.year))

            yearOfMovies.topFiveMovies.forEach {
                list.add(RecyclerViewItem.MovieItem(movie = it))
            }
        }
        return list
    }
}
