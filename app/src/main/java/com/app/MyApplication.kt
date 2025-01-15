package com.app

import android.app.Application
import android.content.Context

class MyApplication:Application() {
    companion object {
        lateinit var context:Context
        val KEY = "ff67b820adcde9d4a846b631c74383b6"
    }

    override fun onCreate() {
        super.onCreate()
        context = baseContext
    }
}