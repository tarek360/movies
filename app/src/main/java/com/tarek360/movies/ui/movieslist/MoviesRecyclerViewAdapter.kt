package com.tarek360.movies.ui.movieslist

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tarek360.movies.R
import com.tarek360.movies.ui.moviedetail.MovieDetailActivity
import com.tarek360.movies.ui.moviedetail.MovieDetailFragment
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MoviesRecyclerViewAdapter(
    private val parentActivity: MoviesListActivity,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val onItemClickListener: ((View, Int) -> Unit)?
    private var items = emptyList<RecyclerViewItem>()

    companion object {
        private const val TYPE_YEAR = 0
        private const val TYPE_MOVIE = 1
    }

    init {
        onItemClickListener = { v, movieId ->
            if (twoPane) {
                val fragment = MovieDetailFragment.newInstance(movieId)

                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, MovieDetailActivity::class.java).apply {
                    putExtra(MovieDetailFragment.ARG_MOVIE_ID, movieId)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_YEAR -> {
                val view = inflater.inflate(R.layout.list_item_year, parent, false)
                YearViewHolder(view)
            }
            TYPE_MOVIE -> {
                val view = inflater.inflate(R.layout.list_item_movie, parent, false)
                MovieViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is RecyclerViewItem.YearItem -> {
                (holder as YearViewHolder).yearView.text = holder.itemView.context.getString(R.string.year, item.year)
            }
            is RecyclerViewItem.MovieItem -> {
                (holder as MovieViewHolder).apply {
                    titleView.text = item.movie.title
                    yearView.text = holder.itemView.context.getString(R.string.year, item.movie.year)
                    itemView.setOnClickListener { v -> onItemClickListener?.invoke(v, item.movie.id) }
                }
            }
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is RecyclerViewItem.YearItem -> TYPE_YEAR
            is RecyclerViewItem.MovieItem -> TYPE_MOVIE
        }
    }

    fun setItems(items: List<RecyclerViewItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.title
        val yearView: TextView = view.year
    }

    inner class YearViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val yearView: TextView = view.year
    }

}
