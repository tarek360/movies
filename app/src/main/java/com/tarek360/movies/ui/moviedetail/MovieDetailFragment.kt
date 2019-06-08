package com.tarek360.movies.ui.moviedetail

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tarek360.movies.App
import com.tarek360.movies.R
import com.tarek360.movies.domain.MovieDetailInteractor
import com.tarek360.movies.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.movie_detail.view.*
import javax.inject.Inject

class MovieDetailFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var movieDetailInteractor: MovieDetailInteractor

    companion object {
        const val ARG_ITEM_INDEX = "item_index"
        fun newInstance(itemId: Int): MovieDetailFragment = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ITEM_INDEX, itemId)
            }
        }
    }

    private var item: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_INDEX)) {
                context?.run {
                    //                    item = MoviesRepositoryImpl(this).movies[it.getInt(ARG_ITEM_INDEX)]
                }
                activity?.toolbar_layout?.title = item?.title
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMoviesList()
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

    private fun loadMoviesList() {
        compositeDisposable.add(movieDetailInteractor.getMovie(getMovieIndex())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                activity?.toolbar_layout?.title = it.title
                view?.yearView?.text = it.year.toString()
            })
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

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onDestroy()
    }
}
