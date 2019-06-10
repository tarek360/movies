package com.tarek360.movies.ui.screens

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.tarek360.movies.R

open class MovieDetailScreen : Screen<MovieDetailScreen>() {
    val yearView = KTextView { withId(R.id.yearView) }
    val genresView = KTextView { withId(R.id.genresView) }
}

