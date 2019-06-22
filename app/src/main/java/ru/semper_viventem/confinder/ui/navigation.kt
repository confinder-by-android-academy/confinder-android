package ru.semper_viventem.confinder.ui

import androidx.fragment.app.Fragment


interface NavigationRouter {
    fun handleMessage(message: NavigationMessage)
}

fun Fragment.sendNavigationMessage(message: NavigationMessage) {
    (activity as? NavigationRouter)?.handleMessage(message)
        ?: throw IllegalStateException("Your activity can't handle navigation message")
}
