package com.example.gamecatalogue.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gamecatalogue.data.http.datasource.GamesDataSource
import com.example.gamecatalogue.data.http.datasource.SearchGamesDataSource
import com.example.gamecatalogue.domain.models.Game
import com.example.gamecatalogue.domain.repositories.GameRepository
import com.example.gamecatalogue.ui.viewmodels.states.GameState
import com.example.gamecatalogue.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val gameRepository: GameRepository
): ViewModel()  {
    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games = _games.asStateFlow()

    var state by mutableStateOf(GameState())
        private set

    private val _query = mutableStateOf("")
    val query = _query

    private val _isLoading = mutableStateOf(true)
    val isLoading = _isLoading

    val gamesPage = Pager(PagingConfig(pageSize = Constants.PAGE_SIZE)) {
        GamesDataSource(gameRepository)
    }.flow.cachedIn(viewModelScope)

    var searchGamesPage = Pager(PagingConfig(pageSize = Constants.PAGE_SIZE)) {
        SearchGamesDataSource(gameRepository, query.value)
    }.flow.cachedIn(viewModelScope)

    fun fetchGames() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            val response = gameRepository.getGames()
            _games.value = response ?: emptyList()

            if(_games.value.isNotEmpty()) {
                _isLoading.value = false
            }
        }
    }

    fun getGameById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            val response = gameRepository.getGameById(id)

            state = state.copy(
                name = response?.name ?: "",
                descriptionRaw = response?.descriptionRaw ?: "",
                metaCritic = response?.metaCritic ?: -1,
                website = response?.website ?: "",
                backgroundImage = response?.backgroundImage ?: ""
            )

            if(state.name.isNotEmpty()) {
                _isLoading.value = false
            }
        }
    }

    fun getGamesByName(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            val response = gameRepository.getGamesByName(query)
            _games.value = response ?: emptyList()

            if(_games.value.isNotEmpty()) {
                _isLoading.value = false
            }
        }
    }

    fun refreshSearchList() {
         searchGamesPage = Pager(PagingConfig(pageSize = Constants.PAGE_SIZE)) {
            SearchGamesDataSource(gameRepository, query.value)
        }.flow.cachedIn(viewModelScope)
    }

    fun cleanState() {
        state = state.copy(
            name =  "",
            descriptionRaw =  "",
            metaCritic =  -1,
            website = "",
            backgroundImage = ""
        )
    }
}