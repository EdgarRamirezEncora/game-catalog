package com.example.gamecatalogue.domain.models

import com.google.gson.annotations.SerializedName

data class GameInfo(
    val name: String,
    @SerializedName("description_raw")
    val descriptionRaw: String,
    @SerializedName("metacritic")
    val metaCritic: Int,
    val website: String,
    @SerializedName("background_image")
    val backgroundImage: String
)
