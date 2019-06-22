package ru.semper_viventem.confinder.data.gateway

import ru.semper_viventem.confinder.data.Profile
import ru.semper_viventem.confinder.data.ProfileCredentials
import ru.semper_viventem.confinder.data.network.ApiService

object AuthGateway {

    private val api = ApiService.api

    private var token: String? = null
    private var profile: Profile? = null

    fun getUser(
        onSuccess: (profile: Profile) -> Unit,
        onError: (e: Throwable) -> Unit
    ) {

        if (token == null) {
            onError.invoke(IllegalStateException("User is not authorized"))
            return
        }

        api.getUser(token!!).execute(
            onSuccess = { profile ->
                this@AuthGateway.profile = profile
                onSuccess.invoke(profile)
            },
            onError = onError
        )
    }

    fun logInWithToken(
        token: String,
        onSuccess: (profile: Profile) -> Unit,
        onError: (e: Throwable) -> Unit
    ) {
        api.getUser(token)
            .execute(
                onSuccess = { profile ->
                    this@AuthGateway.token = token
                    this@AuthGateway.profile = profile
                    onSuccess.invoke(profile)
                },
                onError = onError
            )
    }

    fun postUser(
        profileCredentials: ProfileCredentials,
        onSuccess: (profile: Profile) -> Unit,
        onError: (e: Throwable) -> Unit
    ) {
        if (token == null) {
            onError.invoke(IllegalStateException("User is not authorized"))
            return
        }

        api.postUser(token!!, profileCredentials)
            .execute(
                onSuccess = { profile ->
                    this@AuthGateway.profile = profile
                    onSuccess.invoke(profile)
                },
                onError = onError
            )
    }

    fun getToken(): String? = token

    fun getProfile(): Profile? = profile
}