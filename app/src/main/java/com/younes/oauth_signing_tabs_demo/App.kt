package com.younes.oauth_signing_tabs_demo

import android.app.Application

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}