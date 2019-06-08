package com.tarek360.movies.data

import android.content.Context
import com.google.gson.Gson
import com.tarek360.movies.model.Movie
import com.tarek360.movies.model.MoviesData
import java.nio.charset.Charset

class MoviesRepository(context: Context) {

    companion object {
        const val FILE_NAME = "movies.json"
    }

    val movies: List<Movie> by lazy {
        val inputStream = context.assets.open(FILE_NAME)
        val content = inputStream.readBytes().toString(Charset.defaultCharset())
        inputStream.close()
        val moviesData = Gson().fromJson(content, MoviesData::class.java)
        moviesData.movies
    }
}
