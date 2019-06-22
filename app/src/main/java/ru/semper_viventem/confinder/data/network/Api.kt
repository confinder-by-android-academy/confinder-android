package ru.semper_viventem.confinder.data.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.semper_viventem.confinder.data.Profile
import ru.semper_viventem.confinder.data.ProfileCredentials

interface Api {

    @GET("me")
    fun getUser(@Header("API-KEY") authcode: String): Call<Profile>

    @POST("me")
    fun postUser(
        @Header("API-KEY") authcode: String,
        @Body profile: ProfileCredentials
    ): Call<Profile>
}