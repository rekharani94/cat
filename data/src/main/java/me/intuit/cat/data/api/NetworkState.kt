package me.amitshekhar.newsapp.data.api
sealed class NetworkState {
    object LOADED : NetworkState()
    object LOADING : NetworkState()
    data class ERROR(val msg: String): NetworkState()
}