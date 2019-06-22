package ru.semper_viventem.confinder.ui.profile

import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.screen_profile_edit.*
import ru.semper_viventem.confinder.R
import ru.semper_viventem.confinder.data.Contact
import ru.semper_viventem.confinder.data.ProfileCredentials
import ru.semper_viventem.confinder.data.gateway.AuthGateway
import ru.semper_viventem.confinder.ui.NavigationMessage
import ru.semper_viventem.confinder.ui.XmlScreen
import ru.semper_viventem.confinder.ui.sendNavigationMessage

class ProfileScreen : XmlScreen() {

    companion object {
        private const val SPLIT_PATTERN = ", "
        private const val TAG = "ProfileScreen"
    }

    override val layoutId: Int = R.layout.screen_profile_edit

    override fun initView(view: View) {

        confirm.setOnClickListener {
            sendConfirmData()
        }
    }

    private fun sendConfirmData() {
        val description = description.text.toString()
        val tags = getTags(tags.text.toString())
        val telegramContact = Contact("Telegram", telegram.text.toString())

        val profileCredentials = ProfileCredentials(description, listOf(telegramContact), tags)

        AuthGateway.postUser(
            profileCredentials = profileCredentials,
            onSuccess = { profile ->
                sendNavigationMessage(NavigationMessage.OpenStackScreen)
            },
            onError = {
                Log.e(TAG, it.toString())
                Toast.makeText(context, "Error :(", Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun getTags(tagsStr: String): List<String> {
        return tagsStr.split(SPLIT_PATTERN)
    }
}