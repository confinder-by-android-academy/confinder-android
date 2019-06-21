package ru.semper_viventem.confinder.data.gateway

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.semper_viventem.confinder.data.AuthCodeCredentials
import ru.semper_viventem.confinder.data.network.ApiService

object AuthGateway {

    private val api = ApiService.api
    private var token: String? = null

    fun logInWithToken(token: String, onSuccess: () -> Unit, onError: (e: Throwable) -> Unit) {
        api.sendAuthcode(AuthCodeCredentials(token)).enqueue(
            object : Callback<Unit> {

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    this@AuthGateway.token = token
                    onSuccess.invoke()
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onError.invoke(t)
                }
            }
        )
    }

    fun getToken(): String? = token
}