package com.example.gamecatalogue.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gamecatalogue.ui.viewmodels.GamesViewModel
import com.example.gamecatalogue.ui.views.components.GameCard
import com.example.gamecatalogue.ui.views.components.GameList
import com.example.gamecatalogue.ui.views.components.MainTopBar

@Composable
fun HomeView(
    gamesViewModel: GamesViewModel,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        gamesViewModel.fetchGames()
    }

    Scaffold(
        topBar = {
            MainTopBar(
                title = "Game Catalogue",
                onClickBackButton = {},
                onClickActionsButton = {}
            )
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        HomeViewContent(
            gamesViewModel = gamesViewModel,
            navController = navController,
            paddingValues = it
        )
    }
}

@Composable
fun HomeViewContent(
    gamesViewModel: GamesViewModel,
    navController: NavController,
    paddingValues: PaddingValues
) {
    var query by gamesViewModel.query
    val games = gamesViewModel.allGamesPage.collectAsLazyPagingItems()
    val searchGames = gamesViewModel.searchGamesPage.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = query,
            singleLine = true,
            onValueChange = {
                query = it

                if(query.isNotEmpty()) {
                    searchGames.refresh()
                }
            },
            label = { Text(text = "Search game") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        )

        if(query.isNotEmpty()) {
            GameList(
                games = searchGames,
                navController = navController,
            )
        } else {
            GameList(
                games = games,
                navController = navController,
            )
        }
    }
}


