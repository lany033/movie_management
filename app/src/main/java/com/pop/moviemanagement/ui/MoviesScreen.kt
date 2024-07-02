package com.pop.moviemanagement.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.firebase.analytics.FirebaseAnalytics
import com.pop.moviemanagement.ui.navigation.TrackScreen

@Composable
fun MoviesScreen(analytics: FirebaseAnalytics) {
    TrackScreen(name = "Movies Screen", analytics = analytics)
    Text(text = "Movies Screen")
}