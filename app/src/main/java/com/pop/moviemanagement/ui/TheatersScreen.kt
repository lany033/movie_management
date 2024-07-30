package com.pop.moviemanagement.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pop.moviemanagement.utils.AnalyticsManager
import com.pop.moviemanagement.utils.FirestoreManager

@Composable
fun TheatersScreen(firestoreManager: FirestoreManager) {

    val viewModel: TheaterViewModel = viewModel{
        TheaterViewModel(firestoreManager)
    }

    val theaters by viewModel.state.collectAsState()

    Scaffold(topBar = { MyTopBarScreens("Theaters") }) {
        Column(modifier = Modifier.padding(it)) {

            LazyColumn {
                items(theaters.theaters) { theater ->
                    TheatersItem(
                        theaterName = theater.name,
                        theaterLocation = theater.address,
                        favorite = theater.favorite
                    )

                }
            }
        }
    }
}

@Composable
fun TheatersItem(theaterName: String, theaterLocation: String, favorite: Boolean) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier.align(Alignment.CenterStart),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = "favorite",
                        modifier = Modifier.size(50.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Column {
                    Text(text = theaterName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = theaterLocation)
                }
            }
        }
        IconButton(modifier = Modifier.align(Alignment.CenterEnd), onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.ArrowForwardIos, contentDescription = "", tint = Color.Red)
        }
    }
}