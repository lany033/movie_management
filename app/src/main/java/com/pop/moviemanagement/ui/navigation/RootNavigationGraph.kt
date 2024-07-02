package com.pop.moviemanagement.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.analytics.FirebaseAnalytics
import com.pop.moviemanagement.ui.LoginScreen
import com.pop.moviemanagement.ui.MainScreen

@Composable
fun RootNavigationGraph(navController: NavHostController, analytics: FirebaseAnalytics){
    NavHost(navController = navController, route = Graph.ROOT, startDestination = Graph.HOME){
        composable(route = Graph.HOME){ MainScreen(analytics = analytics) }

    }
}

object Graph{
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val AUTH = "auth_graph"
}