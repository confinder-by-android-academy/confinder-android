package ru.semper_viventem.confinder.data.gateway

import retrofit2.Call
import ru.semper_viventem.confinder.data.Profile
import ru.semper_viventem.confinder.data.network.api

object SwipeListGateway {

    private const val DEFAULT_CONFERENCE_ID = 1

    private val token: String?
        get() = AuthGateway.token

    fun getSwipeList(
        onSuccess: (profiles: List<Profile>) -> Unit,
        onError: (e: Throwable) -> Unit
    ): Call<*>? {

        if (token == null) {
            onError.invoke(IllegalStateException("User is not authorized"))
            return null
        }

        return api.getSwipeList(token!!, DEFAULT_CONFERENCE_ID).enqueue(onSuccess, onError)
    }

    fun like(
        userId: String/*,
        onSuccess: () -> Unit,
        onError: (e: Throwable) -> Unit*/
    ) {

        if (token == null) {
//            onError.invoke(IllegalStateException("User is not authorized"))
            return
        }

        api.like(token!!, userId).enqueue({ /*onSuccess.invoke()*/ }, { } /*onError*/)
    }

    fun dislike(
        userId: String/*,
        onSuccess: () -> Unit,
        onError: (e: Throwable) -> Unit*/
    ) {

        if (token == null) {
//            onError.invoke(IllegalStateException("User is not authorized"))
            return
        }

        api.dislike(token!!, userId).enqueue({ /*onSuccess.invoke()*/ }, { } /*onError*/)
    }

    fun matches(onSuccess: (List<Any /* = Profile | Contact */>) -> Unit, onError: (e: Throwable) -> Unit): Call<*>? =
        if (token === null) {
            onError(IllegalStateException("User is not authorized"))
            null
        } else {
            api.matches(token!!).enqueue({ users ->
                val flattened = ArrayList<Any>(users.size)
                users.forEach {
                    flattened.add(it)
                    flattened.addAll(it.contacts)
                }
                onSuccess(flattened)
            }, onError)
        }

}
