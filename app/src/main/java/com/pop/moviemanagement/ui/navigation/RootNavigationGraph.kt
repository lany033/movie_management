package com.pop.moviemanagement.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseUser
import com.pop.moviemanagement.ui.MainScreen
import com.pop.moviemanagement.ui.authScreen.LoginScreen
import com.pop.moviemanagement.ui.authScreen.SignUpScreen
import com.pop.moviemanagement.utils.AnalyticsManager
import com.pop.moviemanagement.utils.AuthManager

@Composable
fun RootNavigationGraph(context: Context, navController: NavHostController =  rememberNavController()) {
    var analytics: AnalyticsManager = AnalyticsManager(context)
    val authManager: AuthManager = AuthManager(context)

    //val user: FirebaseUser? = authManager.getCurrentUser()

    NavHost(navController = navController, route = Graph.ROOT, startDestination = Graph.HOME) {
        composable(route = Graph.HOME) { MainScreen(analytics, authManager) }

    }
}


object Graph {
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val AUTH = "auth_graph"

}