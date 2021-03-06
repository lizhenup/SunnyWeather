package com.android.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {
    companion object {
        const val TOKEN = "DFnIBcBqivo7J2gn"
        @SuppressLint("StaticFieldLeak")//告诉AS忽略内存泄露
        lateinit var context: Context//全局中只会存在一份，整个应用程序的生命周期都不会回收
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}