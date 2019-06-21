package ru.semper_viventem.confinder.data.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ru.semper_viventem.confinder.data.AuthCodeCredentials

interface Api {

    @POST("authcode")
    fun sendAuthcode(@Body authcode: AuthCodeCredentials): Call<Unit>
}