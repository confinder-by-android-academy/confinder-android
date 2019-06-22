package ru.semper_viventem.confinder.data.network

class NetworkError(
    val errorCode: Int
): Exception("NetworkError")