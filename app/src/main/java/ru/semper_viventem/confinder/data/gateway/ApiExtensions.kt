package ru.semper_viventem.confinder.data.gateway

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.semper_viventem.confinder.data.network.NetworkError

fun <T> Call<T>.execute(onSuccess: (T) -> Unit, onError: (e: Throwable) -> Unit) {
    this.enqueue(
        object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    onSuccess.invoke(response.body()!!)
                } else {
                    onError.invoke(NetworkError(response.code()))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                onError.invoke(t)
            }
        }
    )
}