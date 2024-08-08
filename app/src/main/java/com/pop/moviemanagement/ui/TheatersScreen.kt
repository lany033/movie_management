package com.pop.moviemanagement.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pop.moviemanagement.utils.FirestoreManager

@Composable
fun TheatersScreen(firestoreManager: FirestoreManager) {

    val theaterViewModel: TheaterViewModel = viewModel {
        TheaterViewModel(firestoreManager)
    }

    val theaters by theaterViewModel.theaterState.collectAsState()

    Scaffold(topBar = { MyTopBarScreens("Theaters") }) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(Color.LightGray)
        ) {

            LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {}
                }
                items(theaters.theaters) { theater ->
                    TheatersItem(
                        theaterName = theater.name,
                        theaterLocation = theater.address,
                        favorite = theater.favorite,
                        onFavoriteClick = { isFavorite ->
                            theaterViewModel.onFavoriteClicked(
                                theaterId = theater.id ?: "",
                                isFavorite = isFavorite
                            )
                        },
                        isLogged = theaterViewModel.isLogged()
                    )
                }
            }
        }
    }
}

@Composable
fun TheatersItem(
    theaterName: String,
    theaterLocation: String,
    favorite: Boolean,
    onFavoriteClick: (Boolean) -> Unit,
    isLogged: Boolean
) {

    var isFavorite by remember { mutableStateOf(favorite) }

    Card(shape = RectangleShape) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(top = 15.dp, end = 10.dp, bottom = 15.dp, start = 10.dp)
        ) {
            Card(
                modifier = Modifier.align(Alignment.CenterStart),
                shape = RectangleShape,
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isLogged) {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "favorite",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            isFavorite = !isFavorite
                            onFavoriteClick(isFavorite)
                        }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "favorite",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(5.dp))
                    Column {
                        Text(text = theaterName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Spacer(modifier = Modifier.padding(6.dp))
                        Text(text = theaterLocation)
                    }
                }
            }
            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = { }
            ) {
                Icon(Icons.Filled.ArrowForwardIos, contentDescription = "", tint = Color.Red)
            }
        }
    }

}