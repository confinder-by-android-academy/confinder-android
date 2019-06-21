package ru.semper_viventem.confinder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.semper_viventem.confinder.R
import ru.semper_viventem.confinder.ui.auth.AuthScreen

class MainActivity : AppCompatActivity(), NavigationRouter {

    private val containerId = R.id.container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleMessage(NavigationMessage.OpenAuthScreen)
    }

    override fun handleMessage(message: NavigationMessage) {
        when (message) {
            is NavigationMessage.OpenAuthScreen -> openScreen(AuthScreen())
        }
    }

    private fun openScreen(screen: Screen) {
        supportFragmentManager
            .beginTransaction()
            .add(containerId, screen)
            .commit()
    }
}
