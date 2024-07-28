package com.pop.moviemanagement.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.pop.moviemanagement.model.repository.MovieRepository
import com.pop.moviemanagement.ui.navigation.TrackScreen
import com.pop.moviemanagement.utils.AnalyticsManager

@Composable
fun MoviesScreen() {

    val viewModel: MovieViewModel = viewModel {
        MovieViewModel(MovieRepository())
    }

    val state by viewModel.state.collectAsState()

    Scaffold(topBar = { MyTopBarScreens("Movies") }) {
        Column(modifier = Modifier.padding(it)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(state.movies) { movie ->
                    MovieItem(title = movie.title, image = movie.poster_path)
                }
            }
        }
    }
}

@Composable
fun MyTopBarScreens(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.align(Alignment.TopStart),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Row(
            modifier = Modifier.align(Alignment.TopEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.AccountCircle, contentDescription = "search")
            Icon(Icons.Filled.Search, contentDescription = "search")
        }
    }
}

@Composable
fun MovieItem(title: String, image: String) {
    Card(shape = RectangleShape) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = "https://image.tmdb.org/t/p/w500/${image}",
            contentDescription = "image",
            contentScale = ContentScale.Crop
        )
    }
}

//"https://image.tmdb.org/t/p/w185/${movie.posterPath}"