package ru.semper_viventem.confinder.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.semper_viventem.confinder.BuildConfig

object ApiService {

    val api: Api = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)
}