package com.tarek360.movies.ui.moviedetail

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.tarek360.movies.domain.MovieDetailInteractor
import com.tarek360.movies.livedatatesting.test
import com.tarek360.movies.model.Movie
import com.tarek360.movies.rx.SchedulerProvider
import com.tarek360.movies.shouldEqual
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @Mock
    lateinit var schedulerProvider: SchedulerProvider

    @Mock
    lateinit var movieDetailInteractor: MovieDetailInteractor

    private val viewModel: MovieDetailViewModel by lazy {
        MovieDetailViewModel(schedulerProvider, movieDetailInteractor)
    }

    @Before
    fun setUp() {
        whenever(schedulerProvider.io()).thenReturn(Schedulers.trampoline())
        whenever(schedulerProvider.main()).thenReturn(Schedulers.trampoline())
    }

    @Test
    fun `handle LoadMovieIntent returns DataState`() {

        // Arrange
        val movie = Movie("Face-off", 1997, emptyList(), emptyList(), 5)
        val dataState = MovieDetailState.DataState(data = movie)

        whenever(movieDetailInteractor.getMovie(112)).thenReturn(Observable.just(movie))

        val testObserver = viewModel.viewState.test()

        // Act
        viewModel.handleIntent(MovieDetailIntent.LoadMovieIntent(112))

        // Assert
        testObserver.assertHistorySize(2)
        testObserver.firstValue() shouldEqual MovieDetailState.LoadingState
        testObserver.secondValue() shouldEqual dataState

        // Clean
        viewModel.viewState.removeObserver(testObserver)
    }

    @Test
    fun `handle LoadMovieIntent returns ErrorState`() {

        // Arrange
        val errorState = MovieDetailState.ErrorState(data = "Error")

        whenever(movieDetailInteractor.getMovie(7)).thenReturn(Observable.error(Throwable()))

        val testObserver = viewModel.viewState.test()

        // Act
        viewModel.handleIntent(MovieDetailIntent.LoadMovieIntent(7))

        // Assert
        testObserver.assertHistorySize(2)
        testObserver.firstValue() shouldEqual MovieDetailState.LoadingState
        testObserver.secondValue() shouldEqual errorState

        // Clean
        viewModel.viewState.removeObserver(testObserver)
    }
}
