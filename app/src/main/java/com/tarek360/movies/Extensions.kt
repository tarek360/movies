package com.tarek360.movies

import android.content.Context
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
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

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}
