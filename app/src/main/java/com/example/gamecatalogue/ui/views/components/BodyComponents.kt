package com.example.gamecatalogue.ui.views.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberImagePainter
import com.example.gamecatalogue.R
import com.example.gamecatalogue.domain.models.Game
import com.example.gamecatalogue.ui.viewmodels.GamesViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String,
    showBackButton: Boolean = false,
    onClickBackButton: () -> Unit,
    showActions: Boolean = false,
    onClickActionsButton: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.wrapContentSize()
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            if(showBackButton) {
                IconButton(onClick = { onClickBackButton() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            if (showActions) {
                IconButton(onClick = { onClickActionsButton() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            }
        }
    )
}

@Composable
fun GameCard(
    game: Game,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(10.dp)
            .shadow(40.dp)
            .clickable { onClick() }
    ) {
        Column {
            ImageContainer(imageUrl = game.backgroundImage)
            Text(
                text = game.name,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(start = 10.dp)
            )
        }
    }
    
}

@Composable
fun ImageContainer(imageUrl: String?) {
    val image = rememberImagePainter(data = imageUrl ?: "")
    
    Image(
        painter = image,
        contentDescription = 
        null, 
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}

@Composable
fun MetaWebsite(url: String) {
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

    Column {
        Text(
            text = "MetaScore",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(vertical = 10.dp)
            )
        
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            ),
            enabled = url.isNotEmpty(),
            onClick = { context.startActivity(intent) }
        ) {
            Text(text = "Website")
        }
    }
}

@Composable
fun ReviewCard(metaScore: Int) {
    Card(
        modifier = Modifier
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF209B14)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = metaScore.toString(),
                color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun GameList(
    games: LazyPagingItems<Game>,
    navController: NavController,
    gamesViewModel: GamesViewModel
) {
    val isNetworkAvailable by gamesViewModel.isNetworkAvailable
    val context = LocalContext.current

    if(games.itemCount > 0) {
        LazyColumn {
            items(games.itemCount) {
                val item = games[it]
                if (item != null)
                    GameCard(game = item) {
                        gamesViewModel.checkNetworkStatus(context)
                        if (isNetworkAvailable) {
                            navController.navigate("detail_view/${item.id}")
                        }
                    }
            }
        }
    } else {
        Text(text = "No results.")
    }

    when(games.loadState.append) {
        is LoadState.NotLoading -> {}
        is LoadState.Loading -> {
            CircularProgressIndicator()
        }
        is LoadState.Error -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Something went wrong")
                Button(
                    onClick = { games.refresh() },
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text(text = "Try again")
                }
            }
        }
    }
}

@Composable
fun NoInternetConnection() {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(text = "No internet connection")
        }
    }
}
