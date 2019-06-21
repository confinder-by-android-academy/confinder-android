package ru.semper_viventem.confinder.ui.auth

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.screen_auth.*
import ru.semper_viventem.confinder.BuildConfig
import ru.semper_viventem.confinder.R
import ru.semper_viventem.confinder.ui.XmlScreen
import ru.semper_viventem.confinder.ui.visible

class AuthScreen : XmlScreen() {

    companion object {
        private const val RC_SIGN_IN = 900
        private const val TAG = "AuthScreen"
    }

    override val layoutId: Int = R.layout.screen_auth

    private val gso = GoogleSignInOptions.Builder()
        .requestIdToken(BuildConfig.GOOGLE_AUTH_KEY)
        .requestEmail()
        .build()

    private lateinit var googleSignInClient: GoogleSignInClient


    override fun initView(view: View) {
        googleSignInClient = GoogleSignIn.getClient(activity!!, gso)
        signIn.setOnClickListener { signIn() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            showProgress(false)
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val tokenStr = task.getResult(ApiException::class.java)?.idToken
                tokenStr?.let { handleToken(it) }
            } catch (e: ApiException) {
                Toast.makeText(context, "Could not auth :(", Toast.LENGTH_LONG).show()
                Log.e(TAG, e.toString())
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        showProgress(true)
    }

    private fun handleToken(token: String) {
        Log.d(TAG, token)
    }

    private fun showProgress(show: Boolean) {
        progress.visible(show)
    }
}