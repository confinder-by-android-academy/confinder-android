package ru.semper_viventem.confinder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import ru.semper_viventem.confinder.data.gateway.AuthGateway
import ru.semper_viventem.confinder.ui.NavigationMessage.*
import ru.semper_viventem.confinder.ui.auth.AuthScreen
import ru.semper_viventem.confinder.ui.matches.MatchesScreen
import ru.semper_viventem.confinder.ui.profile.ProfileScreen
import ru.semper_viventem.confinder.ui.stack.StackScreen

class MainActivity : AppCompatActivity(), NavigationRouter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startMessage = if (isAuthorised()) OpenProfileDataScreen else OpenAuthScreen
        handleMessage(startMessage)
    }

    override fun handleMessage(message: NavigationMessage) {
        when (message) {
            OpenAuthScreen -> openScreen(AuthScreen())
            OpenProfileDataScreen -> openScreen(ProfileScreen())
            OpenStackScreen -> openScreen(StackScreen())
            OpenMatchingScreen -> openScreen(MatchesScreen())
        }
    }

    private fun openScreen(screen: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, screen)
            .commit()
    }

    private fun isAuthorised(): Boolean {
        val user = GoogleSignIn.getLastSignedInAccount(this)
        AuthGateway.token = user?.idToken
        return user != null
    }
}
