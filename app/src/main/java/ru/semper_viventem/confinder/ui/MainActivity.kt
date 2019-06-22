package ru.semper_viventem.confinder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.semper_viventem.confinder.ui.auth.AuthScreen
import ru.semper_viventem.confinder.ui.matches.MatchesScreen
import ru.semper_viventem.confinder.ui.profile.ProfileScreen
import ru.semper_viventem.confinder.ui.stack.StackScreen

class MainActivity : AppCompatActivity(), NavigationRouter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleMessage(NavigationMessage.OpenAuthScreen)
    }

    override fun handleMessage(message: NavigationMessage) {
        when (message) {
            NavigationMessage.OpenAuthScreen -> openScreen(AuthScreen())
            NavigationMessage.OpenProfileDataScreen -> openScreen(ProfileScreen())
            NavigationMessage.OpenStackScreen -> openScreen(StackScreen())
            NavigationMessage.OpenMatchingScreen -> openScreen(MatchesScreen())
        }!!
    }

    private fun openScreen(screen: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, screen)
            .commit()
    }
}
