package com.tarek360.movies.ui.moviedetail

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tarek360.movies.App
import com.tarek360.movies.R
import com.tarek360.movies.viewmodel.MovieViewModelProviders
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.movie_detail.*
import timber.log.Timber
import javax.inject.Inject

class MovieDetailFragment : Fragment() {

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    @Inject
    lateinit var movieViewModelProviders: MovieViewModelProviders

    companion object {
        const val ARG_ITEM_INDEX = "item_index"
        fun newInstance(itemId: Int): MovieDetailFragment = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ITEM_INDEX, itemId)
            }
        }
    }

    override fun onAttach(context: Context?) {
        (context?.applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieDetailViewModel = movieViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
        movieDetailViewModel.viewState.observe(this, Observer { render(it) })
        movieDetailViewModel.handleIntent(MovieDetailIntent.LoadMovieIntent(index = getMovieIndex()))
    }

    private fun render(state: MovieDetailState?) {
        if (state == null) return // Ignore null values
        when (state) {
            is MovieDetailState.LoadingState -> renderLoadingState()
            is MovieDetailState.DataState -> renderDataState(state)
            is MovieDetailState.ErrorState -> renderErrorState(state)
        }
    }

    private fun renderLoadingState() {
        Timber.d("Render: loading state")
    }

    private fun renderDataState(dataState: MovieDetailState.DataState) {
        Timber.d("Render: data state")
        activity?.toolbar_layout?.title = dataState.data.title
        yearView.text = dataState.data.year.toString()
    }

    private fun renderErrorState(dataState: MovieDetailState.ErrorState) {
        Timber.d("Render: Error State")
    }

    private fun getMovieIndex(): Int {
        arguments?.let {
            if (it.containsKey(ARG_ITEM_INDEX)) {
                context?.run {
                    return it.getInt(ARG_ITEM_INDEX)
                }
            }
        }
        throw Throwable("Can't get movie index")
    }
}
