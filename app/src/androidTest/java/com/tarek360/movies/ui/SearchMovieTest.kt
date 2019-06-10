package com.tarek360.movies.ui

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.tarek360.movies.ui.movieslist.MoviesListActivity
import com.tarek360.movies.ui.screens.MovieDetailScreen
import com.tarek360.movies.ui.screens.MoviesListScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchMovieTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MoviesListActivity::class.java)

    @Test
    fun searchMovieTest() {
        onScreen<MoviesListScreen> {
            searchTextField {
                typeText("titanic")
            }
            moviesRecycler {
                childAt<MoviesListScreen.MovieItem>(1) {
                    isVisible()
                    title { hasText("Titanic 3D") }
                    year { hasText("Year: 2012") }
                    click()
                }
            }
        }

        onScreen<MovieDetailScreen> {
            yearView { hasText("2012") }
            genresView { hasText("Drama.") }
        }
    }
}
