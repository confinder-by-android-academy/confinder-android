package ru.semper_viventem.confinder.data.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.semper_viventem.confinder.BuildConfig
import ru.semper_viventem.confinder.data.Profile
import ru.semper_viventem.confinder.data.ProfileCredentials

val api: Api = Retrofit.Builder()
    .baseUrl(BuildConfig.API_URL)
    .client(getOkHttp())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(Api::class.java)

private fun getOkHttp() = OkHttpClient.Builder()
    .addNetworkInterceptor(StethoInterceptor())
    .build()

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

    @POST("dislike/{userId}")
    fun dislike(@Header("API-KEY") token: String, @Path("userId") userId: String): Call<Unit>

    @GET("matches")
    fun matches(@Header("API-KEY") token: String): Call<List<Profile>>

}
