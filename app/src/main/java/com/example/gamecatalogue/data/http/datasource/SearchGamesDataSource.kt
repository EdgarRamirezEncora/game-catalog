package com.example.gamecatalogue.data.http.datasource

import androidx.compose.runtime.MutableState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gamecatalogue.domain.models.Game
import com.example.gamecatalogue.domain.repositories.GameRepository
import javax.inject.Inject

class SearchGamesDataSource @Inject constructor(
    private val gamesRepository: GameRepository,
    private val query: String,
): PagingSource<Int, Game>() {
    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        return try {
            val nextPage = params.key ?: 1
            val response = gamesRepository.getGamesByName(query = query, page = nextPage)
            LoadResult.Page(
                data = response ?: emptyList(),
                nextKey = if(response?.isNotEmpty() == true) nextPage + 1 else null,
                prevKey = null
            )
        } catch(e: Exception) {
            LoadResult.Error(e)
        }
    }
}