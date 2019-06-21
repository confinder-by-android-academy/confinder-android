package ru.semper_viventem.confinder.data

import com.google.gson.annotations.SerializedName

data class AuthCodeCredentials(
    @SerializedName("authCode") val authCode: String
)