package ru.semper_viventem.confinder.data

import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("id") val id: String,
    @SerializedName("given_name") val firstName: String,
    @SerializedName("family_name") val lastName: String,
    @SerializedName("picture") val photo: String,
    @SerializedName("description") val description: String,
    @SerializedName("contacts") val contacts: Map<String, String>,
    @SerializedName("tags") val tags: List<String>
)