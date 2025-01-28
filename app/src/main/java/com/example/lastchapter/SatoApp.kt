package com.example.lastchapter

import android.app.Application
import com.example.lastchapter.repository.AppContainer
import com.example.lastchapter.repository.SatoContainer

class SatoApp : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Menginisialisasi container dengan SatoContainer
        container = SatoContainer()
    }
}
