package ru.semper_viventem.confinder.data.gateway

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.semper_viventem.confinder.data.AuthCodeCredentials
import ru.semper_viventem.confinder.data.Profile
import ru.semper_viventem.confinder.data.network.ApiService

object AuthGateway {

    private val api = ApiService.api
    private var token: String? = null
    private var profile: Profile? = null

    fun logInWithToken(
        token: String,
        onSuccess: (profile: Profile) -> Unit,
        onError: (e: Throwable) -> Unit
    ) {
        api.getUser(AuthCodeCredentials(token)).enqueue(
            object : Callback<Profile> {

                override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                    this@AuthGateway.token = token
                    this@AuthGateway.profile = response.body()
                    onSuccess.invoke(profile!!)
                }

                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    onError.invoke(t)
                }
            }
        )
    }

    fun getToken(): String? = token

    fun getProfile(): Profile? = profile
}