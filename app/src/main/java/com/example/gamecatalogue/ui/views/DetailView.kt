package com.example.gamecatalogue.ui.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gamecatalogue.ui.viewmodels.GamesViewModel
import com.example.gamecatalogue.ui.views.components.ImageContainer
import com.example.gamecatalogue.ui.views.components.MainTopBar
import com.example.gamecatalogue.ui.views.components.MetaWebsite
import com.example.gamecatalogue.ui.views.components.ReviewCard

@Composable
fun DetailView(
    gamesViewModel: GamesViewModel,
    navController: NavController,
    gameId: Int
) {
    LaunchedEffect(Unit) {
        gamesViewModel.getGameById(gameId)
    }

    BackHandler {
        gamesViewModel.cleanState()
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            MainTopBar(
                title = gamesViewModel.state.name,
                showBackButton = true,
                onClickBackButton = {
                    navController.popBackStack()
                },
                onClickActionsButton = {}
            )
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        DetailViewContent(
            gamesViewModel = gamesViewModel,
            paddingValues = it
        )
    }
}

@Composable
fun DetailViewContent(
    gamesViewModel: GamesViewModel,
    paddingValues: PaddingValues
) {
    val state = gamesViewModel.state
    val isLoading by gamesViewModel.isLoading

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(isLoading) {
            CircularProgressIndicator()
        } else {
            ImageContainer(imageUrl = state.backgroundImage)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 5.dp)
            ) {
                MetaWebsite(url = state.website)
                ReviewCard(metaScore = state.metaCritic)
            }

            val scroll = rememberScrollState(0)

            Text(
                text = state.descriptionRaw,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(horizontal = 15.dp,)
                    .padding(bottom = 10.dp)
                    .verticalScroll(scroll)
            )
        }
    }
}