package com.example.neighbors.models

data class Neighbor(
    var id: Long,
    val name: String,
    val avatarUrl: String,
    val address: String,
    val phoneNumber: String,
    val aboutMe: String,
    var favorite: Boolean,
    val webSite: String
)
