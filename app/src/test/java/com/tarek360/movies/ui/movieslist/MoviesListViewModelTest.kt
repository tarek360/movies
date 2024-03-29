package com.tarek360.movies.ui.movieslist

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.tarek360.movies.domain.MoviesListInteractor
import com.tarek360.movies.livedatatesting.test
import com.tarek360.movies.mapper.RecyclerViewItemMapper
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
class MoviesListViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @Mock
    lateinit var schedulerProvider: SchedulerProvider

    @Mock
    lateinit var moviesListInteractor: MoviesListInteractor

    @Mock
    lateinit var recyclerViewItemMapper: RecyclerViewItemMapper

    private val viewModel: MoviesListViewModel by lazy {
        MoviesListViewModel(schedulerProvider, moviesListInteractor, recyclerViewItemMapper)
    }

    @Before
    fun setUp() {
        whenever(schedulerProvider.io()).thenReturn(Schedulers.trampoline())
        whenever(schedulerProvider.main()).thenReturn(Schedulers.trampoline())
    }

    @Test
    fun `handle LoadMoviesListIntent returns DataState`() {

        // Arrange
        val movie = Movie(1, "Face-off", 1997, emptyList(), emptyList(), 5)
        val listOfMovies = listOf(movie)
        val listOfRecyclerViewItems = listOf(RecyclerViewItem.MovieItem(movie))

        val dataState = MoviesListState.DataState.AllMoviesState(
            data = listOfRecyclerViewItems
        )

        whenever(moviesListInteractor.getMoviesList()).thenReturn(Observable.just(listOfMovies))
        whenever(recyclerViewItemMapper.mapFromMoviesList(listOfMovies)).thenReturn(listOfRecyclerViewItems)

        val testObserver = viewModel.viewState.test()

        // Act
        viewModel.handleIntent(MoviesListIntent.LoadMoviesListIntent)

        // Assert
        testObserver.assertHistorySize(2)
        testObserver.firstValue() shouldEqual MoviesListState.LoadingState
        testObserver.secondValue() shouldEqual dataState

        // Clean
        viewModel.viewState.removeObserver(testObserver)
    }

    @Test
    fun `handle LoadMoviesListIntent returns ErrorState`() {

        // Arrange
        val errorState = MoviesListState.ErrorState(data = "Error")

        whenever(moviesListInteractor.getMoviesList()).thenReturn(Observable.error(Exception()))

        val testObserver = viewModel.viewState.test()

        // Act
        viewModel.handleIntent(MoviesListIntent.LoadMoviesListIntent)

        // Assert
        testObserver.assertHistorySize(2)
        testObserver.firstValue() shouldEqual MoviesListState.LoadingState
        testObserver.secondValue() shouldEqual errorState

        // Clean
        viewModel.viewState.removeObserver(testObserver)
    }
}
