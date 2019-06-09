package com.tarek360.movies.ui.movieslist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import com.tarek360.movies.App
import com.tarek360.movies.R
import com.tarek360.movies.longToast
import com.tarek360.movies.viewmodel.MovieViewModelProviders
import kotlinx.android.synthetic.main.activity_movies_list.*
import kotlinx.android.synthetic.main.item_list.*
import timber.log.Timber
import javax.inject.Inject

class MoviesListActivity : AppCompatActivity() {

    @Inject
    lateinit var movieViewModelProviders: MovieViewModelProviders

    private lateinit var moviesListViewModel: MoviesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)

        (application as App).appComponent.inject(this)

        setSupportActionBar(toolbar)
        toolbar.title = title

        setupRecyclerView(moviesRecyclerView)

        moviesListViewModel = movieViewModelProviders.of(this).get(MoviesListViewModel::class.java)

        moviesListViewModel.viewState.observe(this, Observer { render(it) })

        moviesListViewModel.handleIntent(MoviesListIntent.LoadMoviesListIntent)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val twoPane = movieDetailContainer != null
        recyclerView.adapter = MoviesRecyclerViewAdapter(this, emptyList(), twoPane)
    }

    private fun render(state: MoviesListState?) {
        if (state == null) return // Ignore null values
        when (state) {
            is MoviesListState.LoadingState -> renderLoadingState()
            is MoviesListState.DataState -> renderDataState(state)
            is MoviesListState.ErrorState -> renderErrorState(state)
        }
    }

    private fun renderLoadingState() {
        Timber.d("Render: loading state")
        moviesRecyclerView.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    private fun renderDataState(dataState: MoviesListState.DataState) {
        Timber.d("Render: data state")
        progressBar.visibility = View.GONE
        moviesRecyclerView.apply {
            isEnabled = true
            (adapter as MoviesRecyclerViewAdapter).setMoviesList(dataState.data)
        }
    }

    private fun renderErrorState(dataState: MoviesListState.ErrorState) {
        Timber.d("Render: Error State")
        longToast(dataState.data)
        progressBar.visibility = View.GONE
    }
}
