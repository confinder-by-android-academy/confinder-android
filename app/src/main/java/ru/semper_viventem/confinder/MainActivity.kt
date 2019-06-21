package ru.semper_viventem.confinder

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), NavigationRouter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun handleMessage(message: NavigationMessage) {
        when (message) {
            is NavigationMessage.OpenAuthScreen -> Log.d("TODO", "Implement this navigation event")
        }
    }
}
