package com.example.gamecatalogue.data.http

import com.example.gamecatalogue.domain.models.GameInfo
import com.example.gamecatalogue.domain.models.Games
import com.example.gamecatalogue.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameApi {
    @GET("${Constants.ENDPOINT}?key=${Constants.API_KEY}")
    suspend fun getGames(
        @Query("page_size") pageSize: Int,
        @Query("page") page: Int
    ): Response<Games>

    @GET("${Constants.ENDPOINT}?key=${Constants.API_KEY}")
    suspend fun getGamesByName(
        @Query(value = "search") query: String,
        @Query("page_size") pageSize: Int,
        @Query("page") page: Int
    ): Response<Games>

    @GET("${Constants.ENDPOINT}/{id}?key=${Constants.API_KEY}")
    suspend fun getGameById(@Path(value = "id") id: Int): Response<GameInfo>
}