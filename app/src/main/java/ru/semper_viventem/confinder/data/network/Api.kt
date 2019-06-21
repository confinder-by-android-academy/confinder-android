package ru.semper_viventem.confinder.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import ru.semper_viventem.confinder.data.AuthCodeCredentials
import ru.semper_viventem.confinder.data.Profile

interface Api {

    @GET("me")
    fun getUser(@Header("API-KEY") authcode: AuthCodeCredentials): Call<Profile>
}