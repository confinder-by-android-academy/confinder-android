package ru.semper_viventem.confinder.data

import com.google.gson.annotations.SerializedName

class Profile(
    @SerializedName("id") val id: String,
    @SerializedName("given_name") val firstName: String,
    @SerializedName("family_name") val lastName: String,
    @SerializedName("picture") val photo: String,
    @SerializedName("description") val description: String,
    @SerializedName("contacts") val contacts: List<Contact>,
    @SerializedName("tags") val tags: List<String>
)

class ProfileCredentials(
    @SerializedName("description") val description: String,
    @SerializedName("contacts") val contacts: List<Contact>,
    @SerializedName("tags") val tags: List<String>
)

class Contact(
    @SerializedName("key") val contactType: String,
    @SerializedName("value") val value: String
)