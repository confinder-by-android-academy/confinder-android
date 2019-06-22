package ru.semper_viventem.confinder.data.network

import retrofit2.Call
import retrofit2.http.*
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

    @GET("swipe-list/{confId}")
    fun getSwipeList(@Header("API-KEY") token: String, @Path("confId") confId: Int): Call<List<Profile>>

    @POST("like/{userId}")
    fun like(@Header("API-KEY") token: String, @Path("userId") userId: String): Call<Unit>

    @POST("like/{userId}")
    fun dislike(@Header("API-KEY") token: String, @Path("userId") userId: String): Call<Unit>
}