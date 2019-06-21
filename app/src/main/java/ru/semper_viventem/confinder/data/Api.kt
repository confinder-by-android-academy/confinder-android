package ru.semper_viventem.confinder.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("authcode")
    fun sendAuthcode(@Body authcode: AuthCodeCredentials): Call<Unit>
}