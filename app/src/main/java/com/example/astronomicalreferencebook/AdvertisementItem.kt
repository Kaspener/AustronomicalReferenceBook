package com.example.astronomicalreferencebook

data class AdvertisementItem(
    val id: Int,
    val title: String,
    var likes: Int = 0,
    val imageResId: Int
)