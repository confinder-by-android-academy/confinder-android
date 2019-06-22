package ru.semper_viventem.confinder

import android.app.Application
import com.facebook.stetho.Stetho

class TheApplicatin : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }
}