package ru.semper_viventem.confinder.ui

sealed class NavigationMessage {
    object OpenAuthScreen : NavigationMessage()
    object OpenProfileDataScreen: NavigationMessage()
}