package com.tarek360.movies.dagger

import com.tarek360.movies.ui.moviedetail.MovieDetailFragment
import com.tarek360.movies.ui.movieslist.MoviesListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        MoviesListModule::class,
        MovieDetailModule::class,
        ViewModelModule::class]
)
interface AppComponent {
    fun inject(target: MoviesListActivity)
    fun inject(target: MovieDetailFragment)
}
