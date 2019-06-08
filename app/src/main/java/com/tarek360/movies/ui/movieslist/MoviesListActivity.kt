package com.tarek360.movies.ui.movieslist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.tarek360.movies.R
import com.tarek360.movies.data.MoviesRepository
import kotlinx.android.synthetic.main.activity_movies_list.*
import kotlinx.android.synthetic.main.item_list.*

class MoviesListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        setupRecyclerView(moviesRecyclerView)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val twoPane = movieDetailContainer != null
        val movies = MoviesRepository(this).movies
        recyclerView.adapter = MoviesRecyclerViewAdapter(this, movies, twoPane)
    }
}
