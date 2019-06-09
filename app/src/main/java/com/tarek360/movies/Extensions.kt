package com.tarek360.movies

import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast

fun Context.longToast(message: CharSequence) {
    showLongToast(this, message)
}

fun Fragment.longToast(message: CharSequence) {
    context?.run {
        showLongToast(this, message)
    }
}

private fun showLongToast(context: Context, message: CharSequence) {
    Toast
        .makeText(context, message, Toast.LENGTH_LONG)
        .show()
}