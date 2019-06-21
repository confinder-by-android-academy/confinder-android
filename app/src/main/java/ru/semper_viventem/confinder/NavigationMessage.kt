package ru.semper_viventem.confinder

sealed class NavigationMessage {
    object OpenAuthScreen : NavigationMessage()
}