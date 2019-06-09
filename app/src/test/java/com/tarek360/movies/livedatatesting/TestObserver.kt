package com.tarek360.movies.livedatatesting

import android.arch.lifecycle.LiveData

fun <T> LiveData<T>.test(): TestObserver<T> {
    return TestObserver.test(this)
}
