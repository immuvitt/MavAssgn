package com.mavassgn.utils

import android.content.Context
import android.widget.Toast

object UtilExtensions {

    fun Context.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}