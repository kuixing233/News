package com.app.util

import android.widget.Toast
import com.app.MyApplication

fun String.showToast() {
    Toast.makeText(MyApplication.context, this, Toast.LENGTH_SHORT)
        .show()
}