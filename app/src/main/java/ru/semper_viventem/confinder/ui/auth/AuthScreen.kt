package ru.semper_viventem.confinder.ui.auth

import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.screen_auth.*
import ru.semper_viventem.confinder.R
import ru.semper_viventem.confinder.ui.XmlScreen

class AuthScreen : XmlScreen() {

    override val layoutId: Int = R.layout.screen_auth

    override fun initView(view: View) {
        signIn.setOnClickListener { signIn() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // TODO handle google auth result
    }

    private fun signIn() {
        // TODO send google auth intent
    }
}