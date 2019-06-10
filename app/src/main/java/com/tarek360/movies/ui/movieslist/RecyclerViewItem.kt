package com.tarek360.movies.ui.movieslist

import com.tarek360.movies.model.Movie

sealed class RecyclerViewItem {
    data class YearItem(val year: Int) : RecyclerViewItem()
    data class MovieItem(val movie: Movie) : RecyclerViewItem()
}
