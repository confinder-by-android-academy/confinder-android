package ru.semper_viventem.confinder

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes


abstract class App(
    @DrawableRes val iconRes: Int
) {
    abstract fun open(activity: Activity, contact: String)
}

object Telegram : App(
    R.drawable.ic_telegram_app
) {
    override fun open(activity: Activity, contact: String) {
        activity.startActivity(Intent(Intent.ACTION_VIEW,
            Uri.parse("tg://resolve").buildUpon().appendQueryParameter("domain", contact).build()
        ))
    }
}
