package com.tarek360.movies.ui.moviedetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tarek360.movies.R
import com.tarek360.movies.data.MoviesRepository
import com.tarek360.movies.model.Movie
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.movie_detail.view.*

class MovieDetailFragment : Fragment() {

    companion object {
        const val ARG_ITEM_ID = "item_id"
        fun newInstance(itemId: Int): MovieDetailFragment = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ITEM_ID, itemId)
            }
        }
    }

    private var item: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                context?.run {
                    item = MoviesRepository(this).movies[it.getInt(ARG_ITEM_ID)]
                }
                activity?.toolbar_layout?.title = item?.title
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.movie_detail, container, false)

        item?.let {
            rootView.item_detail.text = it.year.toString()
        }
        return rootView
    }

}
