package com.tarek360.movies.ui.movieslist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.tarek360.movies.App
import com.tarek360.movies.R
import com.tarek360.movies.domain.MoviesListInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movies_list.*
import kotlinx.android.synthetic.main.item_list.*
import javax.inject.Inject

class MoviesListActivity : AppCompatActivity() {

    private var adapter: MoviesRecyclerViewAdapter? = null
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var moviesListInteractor: MoviesListInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)

        (application as App).appComponent.inject(this)

        setSupportActionBar(toolbar)
        toolbar.title = title

        setupRecyclerView(moviesRecyclerView)

        loadMoviesList()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val twoPane = movieDetailContainer != null
        adapter = MoviesRecyclerViewAdapter(this, emptyList(), twoPane)
        recyclerView.adapter = adapter
    }

    private fun loadMoviesList() {
        compositeDisposable.add(moviesListInteractor.getMoviesList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter?.setMoviesList(it)
            })
    }

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        moviesListInteractor.clear()
        super.onDestroy()
    }
}
