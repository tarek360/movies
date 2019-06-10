package com.tarek360.movies.ui.moviedetail

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tarek360.movies.App
import com.tarek360.movies.R
import com.tarek360.movies.longToast
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
        const val ARG_MOVIE_ID = "movie_id"
        fun newInstance(itemId: Int): MovieDetailFragment = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_MOVIE_ID, itemId)
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
        movieDetailViewModel.handleIntent(MovieDetailIntent.LoadMovieIntent(movieId = getMovieId()))
        setupPhotosRecyclerView()
    }

    private fun setupPhotosRecyclerView() {
        photosRecyclerView.layoutManager = GridLayoutManager(context, 2)
        photosRecyclerView.adapter = PhotosRecyclerViewAdapter()
    }

    private fun render(state: MovieDetailState?) {
        if (state == null) return // Ignore null values
        when (state) {
            is MovieDetailState.LoadingState -> renderLoadingState(state)
            is MovieDetailState.DataState -> renderDataState(state)
            is MovieDetailState.ErrorState -> renderErrorState(state)
        }
    }

    private fun renderLoadingState(loadingState: MovieDetailState.LoadingState) {
        Timber.d("Render: loading state")
        when (loadingState) {
            is MovieDetailState.LoadingState.MovieLoadingState -> movieProgressBar.visibility = View.VISIBLE
            is MovieDetailState.LoadingState.PhotosLoadingState -> photosProgressBar.visibility = View.VISIBLE
        }
    }

    private fun renderDataState(dataState: MovieDetailState.DataState) {
        Timber.d("Render: data state")
        movieProgressBar.visibility = View.GONE
        activity?.toolbar_layout?.title = dataState.data.title
        yearView.text = dataState.data.year
        castView.text = dataState.data.cast
        genresView.text = dataState.data.genres

        if (dataState.data.photos.isNotEmpty()) {
            photosProgressBar.visibility = View.GONE
            renderPhotos(dataState.data.photos)
        }
    }

    private fun renderPhotos(photos: List<String>) {
        (photosRecyclerView.adapter as PhotosRecyclerViewAdapter).addItems(photos)
    }

    private fun renderErrorState(dataState: MovieDetailState.ErrorState) {
        Timber.d("Render: Error State")
        longToast(dataState.data)
        movieProgressBar.visibility = View.GONE
        photosProgressBar.visibility = View.GONE
    }

    private fun getMovieId(): Int {
        arguments?.let {
            if (it.containsKey(ARG_MOVIE_ID)) {
                context?.run {
                    return it.getInt(ARG_MOVIE_ID)
                }
            }
        }
        throw Throwable("Can't get movie id")
    }
}
