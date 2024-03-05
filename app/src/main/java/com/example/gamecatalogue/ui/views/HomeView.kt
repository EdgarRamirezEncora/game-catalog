package com.example.gamecatalogue.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gamecatalogue.ui.viewmodels.GamesViewModel
import com.example.gamecatalogue.ui.views.components.GameList
import com.example.gamecatalogue.ui.views.components.MainTopBar
import com.example.gamecatalogue.ui.views.components.NoInternetConnection

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(
    gamesViewModel: GamesViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        gamesViewModel.checkNetworkStatus(context)
        if(gamesViewModel.isNetworkAvailable.value) {
            gamesViewModel.fetchGames()
        }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeViewContent(
    gamesViewModel: GamesViewModel,
    navController: NavController,
    paddingValues: PaddingValues
) {
    var query by gamesViewModel.query
    val games = gamesViewModel.allGamesPage.collectAsLazyPagingItems()
    val searchGames = gamesViewModel.searchGamesPage.collectAsLazyPagingItems()

    val context = LocalContext.current
    val state = rememberPullToRefreshState()

    if (state.isRefreshing) {
        LaunchedEffect(true) {
            // fetch something
            gamesViewModel.checkNetworkStatus(context)

            if(gamesViewModel.isNetworkAvailable.value) {
                games.refresh()
                query = ""
            }
            state.endRefresh()
        }
    }

    Box(
        modifier = Modifier.nestedScroll(state.nestedScrollConnection)
    ) {
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

                    if (query.isNotEmpty() && gamesViewModel.isNetworkAvailable.value) {
                        searchGames.refresh()
                    }
                },
                label = { Text(text = "Search game") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            )

            if(gamesViewModel.isNetworkAvailable.value) {
                if (query.isNotEmpty()) {
                    GameList(
                        games = searchGames,
                        navController = navController,
                        gamesViewModel = gamesViewModel
                    )
                } else {
                    GameList(
                        games = games,
                        navController = navController,
                        gamesViewModel = gamesViewModel
                    )
                }
            } else {
                NoInternetConnection()
            }
        }

        PullToRefreshContainer(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp),
            state = state,
        )
    }
}


