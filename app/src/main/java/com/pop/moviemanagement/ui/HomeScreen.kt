package com.pop.moviemanagement.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.pop.moviemanagement.ui.navigation.TrackScreen

@Composable
fun HomeScreen(analytics: FirebaseAnalytics, onClickLoginSession: () -> Unit){
    TrackScreen(name = "Home Screen", analytics = analytics)
    Scaffold(topBar = { MyTopBar("Movie Management", onClickLoginSession ) }) {
        Column(modifier = Modifier
            .padding(it)
            .padding(12.dp)) {
            Text(text = "Home")
            Button(onClick = {
                analytics.logEvent("Test"){
                    param("valor1","test1")
                }
            }) {
                Text(text = "Button")
            }
        }
    }
}
@Composable
fun MyTopBar(title: String, onClickLoginSession: () -> Unit){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)) {
        Card(modifier = Modifier.align(Alignment.TopStart)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Row(modifier = Modifier.align(Alignment.TopEnd), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onClickLoginSession() }) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Account")
            }
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        }

    }
}

@Composable
fun MyTopBarSession(title: String){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)) {
        Row(modifier = Modifier.align(Alignment.TopStart), verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}

