package com.appadore.quiz.extensions

import android.view.View

fun View.show(show: Boolean = true) {
    visibility = if (show) View.VISIBLE else View.GONE
}