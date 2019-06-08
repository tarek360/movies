package com.tarek360.movies.ui.movieslist

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tarek360.movies.ui.moviedetail.MovieDetailActivity
import com.tarek360.movies.ui.moviedetail.MovieDetailFragment
import com.tarek360.movies.R
import com.tarek360.movies.model.Movie
import kotlinx.android.synthetic.main.movies_list_content.view.*

class MoviesRecyclerViewAdapter(
    private val parentActivity: MoviesListActivity,
    private val values: List<Movie>,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>() {

    private val onItemClickListener: ((View, Int) -> Unit)?

    init {
        onItemClickListener = { v, position ->
            //            val item = v.tag as Movie
            if (twoPane) {
                val fragment = MovieDetailFragment.newInstance(position)

                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, MovieDetailActivity::class.java).apply {
                    putExtra(MovieDetailFragment.ARG_ITEM_ID, position)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movies_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleView.text = item.title
        holder.yearView.text = holder.itemView.context.getString(R.string.year, item.year)

        with(holder.itemView) {
            setOnClickListener { v -> onItemClickListener?.invoke(v, position) }
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.title
        val yearView: TextView = view.year
    }
}
