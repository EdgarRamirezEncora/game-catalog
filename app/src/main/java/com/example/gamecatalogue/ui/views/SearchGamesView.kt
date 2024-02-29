package com.example.gamecatalogue.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gamecatalogue.R
import com.example.gamecatalogue.ui.viewmodels.GamesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchGamesView(
    gamesViewModel: GamesViewModel,
    navController: NavController
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val games by gamesViewModel.games.collectAsState()

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Top,
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            query = query,
            onQueryChange = {
                query = it
                gamesViewModel.getGamesByName(query)
            },
            onSearch = { active = true },
            active = active,
            onActiveChange = { active = it},
            placeholder = {
                Text(text = "Search game")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            },
            trailingIcon = {
                if(query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(
                            painter = painterResource(id = R.drawable.clear),
                            contentDescription = "Clear"
                        )
                    }
                }

            }
        ) {
            if(query.isNotEmpty()) {
                val isLoading by gamesViewModel.isLoading
                val scroll = rememberScrollState(0)
                
                if(isLoading) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .verticalScroll(scroll)
                            .fillMaxWidth()
                    ) {
                        games.forEach {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                                    .clickable {
                                        navController.navigate("detail_view/${it.id}")
                                    }
                            ) {
                                Text(text = it.name)
                            }
                        }
                    }
                }
            }

        }
    }
}