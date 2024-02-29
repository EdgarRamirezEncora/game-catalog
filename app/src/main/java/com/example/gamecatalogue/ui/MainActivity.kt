package com.example.gamecatalogue.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gamecatalogue.ui.navigation.NavManager
import com.example.gamecatalogue.ui.theme.GameCatalogueTheme
import com.example.gamecatalogue.ui.viewmodels.GamesViewModel
import com.example.gamecatalogue.ui.views.HomeView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val gamesViewModel: GamesViewModel by viewModels()
            GameCatalogueTheme(
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavManager(gamesViewModel = gamesViewModel)
                }
            }
        }
    }
}