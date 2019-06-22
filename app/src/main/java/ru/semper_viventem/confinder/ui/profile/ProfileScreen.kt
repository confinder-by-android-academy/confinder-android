package ru.semper_viventem.confinder.ui.profile

import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.screen_profile_edit.*
import ru.semper_viventem.confinder.R
import ru.semper_viventem.confinder.data.Contact
import ru.semper_viventem.confinder.data.Profile
import ru.semper_viventem.confinder.data.ProfileCredentials
import ru.semper_viventem.confinder.data.gateway.AuthGateway
import ru.semper_viventem.confinder.ui.NavigationMessage
import ru.semper_viventem.confinder.ui.XmlScreen
import ru.semper_viventem.confinder.ui.sendNavigationMessage

class ProfileScreen : XmlScreen() {

    companion object {
        private const val SPLIT_PATTERN = ", "
        private const val TAG = "ProfileScreen"
        private const val TELEGRAM_VALUE = "Telegram"
    }

    override val layoutId: Int = R.layout.screen_profile_edit

    override fun initView(view: View) {

        initProfile()

        confirm.setOnClickListener {
            sendConfirmData()
        }
    }

    private fun sendConfirmData() {
        val description = description.text.toString()
        val tags = getTags(tags.text.toString())
        val telegramContact = Contact(TELEGRAM_VALUE, telegram.text.toString())

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

    private fun initProfile() {
        AuthGateway.getUser(
            onSuccess = { profile ->
                drawProfile(profile)
            },
            onError = { error ->
                Toast.makeText(context, "Error :(", Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun drawProfile(profile: Profile) {
        description.setText(profile.description)
        tags.setText(profile.tags.joinToString(separator = SPLIT_PATTERN))
        telegram.setText(profile.contacts.firstOrNull { it.contactType == TELEGRAM_VALUE }?.value.orEmpty())

        firstName.text = profile.firstName
        lastName.text = profile.lastName

        Glide.with(context!!)
            .load(profile.photo)
            .apply(RequestOptions.circleCropTransform())
            .into(image)
    }

    private fun getTags(tagsStr: String): List<String> {
        return tagsStr.split(SPLIT_PATTERN)
    }
}