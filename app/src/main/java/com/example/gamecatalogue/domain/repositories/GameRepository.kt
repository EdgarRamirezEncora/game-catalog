package com.example.gamecatalogue.domain.repositories

import com.example.gamecatalogue.data.http.GameApi
import com.example.gamecatalogue.domain.models.Game
import com.example.gamecatalogue.domain.models.GameInfo
import com.example.gamecatalogue.util.Constants
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val gameApi: GameApi
)  {

    suspend fun getGames(
        page: Int = 1,
        pageSize: Int = Constants.PAGE_SIZE
    ): List<Game>? {
        val response = gameApi
            .getGames(pageSize = pageSize, page = page)

        if(response.isSuccessful) {
            return response.body()?.results
        }

        return null
    }

    suspend fun getGameById(id: Int): GameInfo? {
        val response = gameApi.getGameById(id)

        if(response.isSuccessful) {
            return response.body()
        }

        return null
    }

    suspend fun getGamesByName(
        query: String,
        page: Int = 1,
        pageSize: Int = Constants.PAGE_SIZE
    ): List<Game>? {
        val response = gameApi
            .getGamesByName(query = query, pageSize = pageSize, page = page)

        if(response.isSuccessful) {
            return response.body()?.results
        }

        return null
    }
}