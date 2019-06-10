package com.tarek360.movies.data

import android.content.Context
import com.google.gson.Gson
import com.tarek360.movies.model.Movie
import com.tarek360.movies.model.MoviesData
import io.reactivex.Observable
import java.nio.charset.Charset

class MoviesRepositoryImpl(private val context: Context): MoviesRepository {

    companion object {
        const val FILE_NAME = "movies.json"
    }

    private var moviesData: MoviesData? = null

    override fun getMoviesObservable(): Observable<List<Movie>> =
        Observable.create<List<Movie>> {
            val moviesData = moviesData ?: readMoviesData()
            it.onNext(moviesData.movies)
        }

    override fun clear() {
        moviesData = null
    }

    private fun readMoviesData(): MoviesData {
        val inputStream = context.assets.open(FILE_NAME)
        val content = inputStream.readBytes().toString(Charset.defaultCharset())
        inputStream.close()
        val moviesData = Gson().fromJson(content, MoviesData::class.java)
        this.moviesData = moviesData.copy(movies = moviesData.movies.withIds())
        return moviesData
    }

    private fun List<Movie>.withIds(): List<Movie> {
        return this.mapIndexed { index, movie ->
            movie.copy(id = index)
        }
    }
}
