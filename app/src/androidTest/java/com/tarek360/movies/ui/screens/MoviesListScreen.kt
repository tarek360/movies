package com.tarek360.movies.ui.screens

import android.view.View
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.tarek360.movies.R
import org.hamcrest.Matcher

open class MoviesListScreen : Screen<MoviesListScreen>() {

    val searchTextField = KEditText { withId(R.id.searchTextField) }

    val moviesRecycler: KRecyclerView = KRecyclerView({
        withId(R.id.moviesRecyclerView)
    }, itemTypeBuilder = {
        itemType(MoviesListScreen::MovieItem)
    })

    class MovieItem(parent: Matcher<View>) : KRecyclerItem<MovieItem>(parent) {
        val title: KTextView = KTextView(parent) { withId(R.id.title) }
        val year: KTextView = KTextView(parent) { withId(R.id.year) }
    }
}

