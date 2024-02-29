package com.example.gamecatalogue.domain.models

import com.google.gson.annotations.SerializedName

data class Games(
    val count: Int,
    val results: List<Game>
)

data class Game(
    val id: Int,
    val name: String,
    @SerializedName("background_image")
    val backgroundImage: String
)
