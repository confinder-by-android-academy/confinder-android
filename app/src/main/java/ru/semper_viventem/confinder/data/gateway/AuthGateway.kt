package ru.semper_viventem.confinder.data.gateway

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.semper_viventem.confinder.data.Profile
import ru.semper_viventem.confinder.data.ProfileCredentials
import ru.semper_viventem.confinder.data.network.ApiService
import ru.semper_viventem.confinder.data.network.NetworkError

object AuthGateway {

    private val api = ApiService.api
    private var token: String? = null
    private var profile: Profile? = null

    @Synchronized
    fun logInWithToken(
        token: String,
        onSuccess: (profile: Profile) -> Unit,
        onError: (e: Throwable) -> Unit
    ) {
        api.getUser(token).enqueue(
            object : Callback<Profile> {

                override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                    this@AuthGateway.token = token
                    this@AuthGateway.profile = response.body()
                    if (response.isSuccessful) {
                        onSuccess.invoke(profile!!)
                    } else {
                        onError.invoke(NetworkError(response.code()))
                    }
                }

                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    onError.invoke(t)
                }
            }
        )
    }

    @Synchronized
    fun postUser(
        profileCredentials: ProfileCredentials,
        onSuccess: (profile: Profile) -> Unit,
        onError: (e: Throwable) -> Unit
    ) {
        if (token == null) {
            onError.invoke(IllegalStateException("User is not authorized"))
        }

        api.postUser(token!!, profileCredentials).enqueue(
            object : Callback<Profile> {
                override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                    this@AuthGateway.profile = response.body()
                    if (response.isSuccessful) {
                        onSuccess.invoke(profile!!)
                    } else {
                        onError.invoke(NetworkError(response.code()))
                    }
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