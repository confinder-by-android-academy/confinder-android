package ru.semper_viventem.confinder.data.gateway

import ru.semper_viventem.confinder.data.Profile
import ru.semper_viventem.confinder.data.network.api

object SwipeListGateway {

    private const val DEFAULT_CONFERENCE_ID = 1

    private val token: String?
        get() = AuthGateway.token

    fun getSwipeList(
        onSuccess: (profiles: List<Profile>) -> Unit,
        onError: (e: Throwable) -> Unit
    ) {

        if (token == null) {
            onError.invoke(IllegalStateException("User is not authorized"))
            return
        }

        api.getSwipeList(token!!, DEFAULT_CONFERENCE_ID).execute(onSuccess, onError)
    }

    fun like(
        userId: String,
        onSuccess: () -> Unit,
        onError: (e: Throwable) -> Unit
    ) {


        if (token == null) {
            onError.invoke(IllegalStateException("User is not authorized"))
            return
        }

        api.like(token!!, userId).execute({ onSuccess.invoke() }, onError)
    }

    fun dislike(
        userId: String,
        onSuccess: () -> Unit,
        onError: (e: Throwable) -> Unit
    ) {


        if (token == null) {
            onError.invoke(IllegalStateException("User is not authorized"))
            return
        }

        api.dislike(token!!, userId).execute({ onSuccess.invoke() }, onError)
    }
}